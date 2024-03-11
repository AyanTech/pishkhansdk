package ir.ayantech.pishkhansdk.helper

import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.networking.simpleCallInvoiceInfo
import ir.ayantech.networking.simpleCallInvoicePayment
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.enums.PrerequisitesType
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.api.InvoicePayment

import ir.ayantech.pishkhansdk.model.api.InvoiceRegister
import ir.ayantech.pishkhansdk.model.app_logic.OTP
import ir.ayantech.pishkhansdk.model.app_logic.createCallBackLink
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleJusticeSharesPortfolioOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleSubventionHistoryOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleTrafficFinesCarSummaryOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleTrafficFinesCarOutput
import ir.ayantech.pishkhansdk.helper.PishkhanSDK.serviceName
import ir.ayantech.pishkhansdk.model.api.InvoiceInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.pishkhansdk.ui.bottom_sheet.OtpBottomSheetDialog
import ir.ayantech.pishkhansdk.ui.bottom_sheet.PreviewBottomSheetDialog
import ir.ayantech.whygoogle.helper.fromJsonToObject
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.toJsonString

object PaymentHelper {

    var otpBottomSheetDialog: OtpBottomSheetDialog? = null

    /**
     * This method checks if the service has a prerequisite
     **/

    fun invoiceRegister(
        inputModel: BaseInputModel,
        failureCallBack: FailureCallback? = null,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {

        PishkhanSDK.serviceApi.ayanCall<InvoiceRegister.Output>(
            AyanCallStatus {
                success {
                    otpBottomSheetDialog?.dismiss()
                    it.response?.Parameters?.let { output ->
                        when (output.Prerequisites) {
                            //Service doesn't have any prerequisites so should check whether it has a preview or not
                            null -> {
                                if (output.PaymentChannels != null) {

                                    if (output.TermsAndConditions == null && output.Invoice.Service.Summary == null) {
                                        //show payment url page
                                        openOnlinePaymentUrl(
                                            invoiceOutput = output,
                                        )
                                    } else {
                                        //show preview dialog with amount like 2600 or 5200
                                        showPreviewDialog(
                                            inputModel = inputModel,
                                            output = output,
                                            showAmountSection = true,
                                        )

                                    }

                                } else {

                                    if (output.TermsAndConditions != null) {
                                        showPreviewDialog(
                                            inputModel = inputModel,
                                            output = output,
                                            showAmountSection = false,
                                        )
                                    } else {
                                        doProperServiceCall(
                                            inputModel = inputModel,
                                            purchaseKey = output.Invoice.PurchaseKey,
                                        ) {
                                            handleResultCallback?.invoke(it)
                                        }
                                    }
                                }
                            }

                            //Service has prerequisites so should fulfill it
                            else -> {
                                when (output.Prerequisites.Type) {
                                    PrerequisitesType.OTP.name -> {
                                        otpBottomSheetDialog = OtpBottomSheetDialog(
                                            context = PishkhanSDK.whyGoogleActivity,
                                            otp = output.Prerequisites.Value?.fromJsonToObject<OTP>(),
                                        ) { otpCode ->
                                            //if otp code was null,it means that user has clicked retryTv
                                            if (otpCode == null) {
                                                invoiceRegister(
                                                    inputModel = inputModel,
                                                )
                                            } else {
                                                //call invoice register again and put user entered otp to service input
                                                invoiceRegister(
                                                    inputModel = inputModel.also {
                                                        it.OTPCode = otpCode
                                                    },
                                                )
                                            }
                                        }
                                        otpBottomSheetDialog?.show()
                                    }
                                }
                            }
                        }
                    }
                }
                failure {
                    if (otpBottomSheetDialog?.isShowing == true) {
                        otpBottomSheetDialog?.setError(it.failureMessage)
                        if (failureCallBack != null) {
                            failureCallBack(it)
                        }
                    } else {

                        this.ayanCommonCallingStatus?.dispatchFail(it)
                    }
                }
            }, endPoint = EndPoints.InvoiceRegister, input = InvoiceRegister.Input(
                Parameters = inputModel.toJsonString(), Service = serviceName
            )
        )
    }

    private fun openOnlinePaymentUrl(
        invoiceOutput: InvoiceRegister.Output,
    ) {
        invoiceOutput.PaymentChannels?.get(0)?.Gateways?.let { gateways ->
            invoicePayment(
                callBack = CallbackDataModel(
                    sourcePage = "factor",
                    purchaseKey = invoiceOutput.Invoice.PurchaseKey,
                    paymentStatus = "#status#",
                    selectedGateway = null,
                    useCredit = "false",
                    serviceName = invoiceOutput.Invoice.Service.Type.Name,
                    channelName = invoiceOutput.PaymentChannels[0].Type.Name
                ).createCallBackLink(),
                gateway = gateways[0].Type.Name,
                purchaseKey = invoiceOutput.Invoice.PurchaseKey,
                useCredit = false,
            )
        }
    }

    private fun showPreviewDialog(
        inputModel: BaseInputModel,
        output: InvoiceRegister.Output,
        showAmountSection: Boolean,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PreviewBottomSheetDialog(
            context = PishkhanSDK.whyGoogleActivity,
            invoiceOutput = output,
            showAmountSection = showAmountSection
        ) {
            if (showAmountSection) {
                openOnlinePaymentUrl(
                    invoiceOutput = output,
                )
            } else {
                doProperServiceCall(
                    inputModel = inputModel,
                    purchaseKey = output.Invoice.PurchaseKey,
                ) {
                    handleResultCallback?.invoke(it)
                }
            }
        }.show()
    }

    private fun doProperServiceCall(
        inputModel: BaseInputModel,
        purchaseKey: String,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        inputModel.also { it.PurchaseKey = purchaseKey }.let { input ->
            when (serviceName) {
                Products.justiceSharesProduct.name -> {
                    handleJusticeSharesPortfolioOutput(
                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.subventionHistoryProduct.name -> {
                    handleSubventionHistoryOutput(

                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.carTrafficFinesProduct.name -> {
                    handleTrafficFinesCarOutput(

                        input = input,
                        endPoint = EndPoints.TrafficFinesCar,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.carTrafficFinesSummaryProduct.name -> {
                    handleTrafficFinesCarSummaryOutput(

                        input = input,
                        endPoint = EndPoints.TrafficFinesCarSummary,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.motorTrafficFinesProduct.name -> {
                    handleTrafficFinesCarOutput(

                        input = input,
                        endPoint = EndPoints.TrafficFinesMotorcycle,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.motorTrafficFinesSummeryProduct.name -> {
                    handleTrafficFinesCarSummaryOutput(

                        input = input,
                        endPoint = EndPoints.TrafficFinesMotorcycleSummary,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }


                else -> {}

            }
        }

        /*

                        Products.plateNumbersProduct.name -> {
                            handleVehiclePlateNumbersOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.negativePointProduct.name -> {
                            handleDrivingLicenseNegativePointOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.exitBanStatusProduct.name -> {
                            handleExitBanStatusOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.passportStatusProduct.name -> {
                            handlePassportStatusOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.drivingLicenceStatusProduct.name -> {
                            handleDrivingLicenseStatusOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.identificationDocumentsStatusCarProduct.name -> {
                            handleCarDocumentsStatusOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.technicalExaminationCertificateProduct.name -> {
                            handleTechnicalExaminationCertificateOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.socialSecurityInsuranceMedicalCreditProduct.name -> {
                            handleMedicalCreditOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.socialSecurityInsuranceRetirementReceiptProduct.name -> {
                            handleSocialSecuritySalaryOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.socialSecurityInsuranceRetirementProduct.name -> {
                            handleSocialSecurityRetirementOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.socialSecurityInsuranceHistoryProduct.name -> {
                            handleSocialSecurityInsuranceHistoryOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.governmentRetirementProduct.name -> {
                            handleGovernmentRetirementOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.governmentRetirementSalaryReceiptProduct.name -> {
                            handleGovernmentSalaryOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.waterBillProduct.name -> {
                            handleWaterBillOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.electricityBillProduct.name -> {
                            handleElectricityBillOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.gasBillByIdentifierProduct.name -> {
                            handleGasBillByIdentifierOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.gasBillByParticipateCodeProduct.name -> {
                            handleGasBillByParticipateCodeOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.landlinePhoneBillProduct.name -> {
                            handleLandLineBillOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.mobileProduct.name -> {
                            handleMobileBillOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.subventionHistoryProduct.name -> {
                            handleSubventionHistoryOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.propertyTollsProduct.name -> {
                            handlePropertyTollOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.taxiFaresBillProduct.name -> {
                            handleTaxiFaresBillOutput(
                                ayanActivity = activity, input = input
                            )
                        }

                        Products.freewayTollBillsProduct.name -> {
                            handleFreewayTollsOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.annualTollCarProductProduct.name -> {
                            handleAnnualTollOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.trafficPlanTollCarProduct.name -> {
                            handleTrafficPlanTollOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.transferTaxCarProduct.name -> {
                            handleTransferTaxCarOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.transferTaxMotorcycleProduct.name -> {
                            handleTransferTaxMotorcycleOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.postPackageTrackingProduct.name -> {
                            handlePostPackageOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.sayadChequeProduct.name -> {
                            handleSayadChekOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.thirdPartyInsuranceProduct.name -> {
                            handleThirdPartyInsuranceOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.thirdPartyInsuranceStatusProduct.name -> {
                            handleThirdPartyInsuranceStatusOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.ibanByCardNumberProduct.name -> {
                            handleCardOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.accountNumberByIbanProduct.name -> {
                            handleShebaOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.ibanByAccountNumberProduct.name -> {
                            handleAccountOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.identificationDocumentsStatusMotorcycleProduct.name -> {
                            handleMotorDocumentsStatusOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.socialSecurityInsuranceMedicalPrescriptionProduct.name -> {
                            handleSocialSecurityInsuranceMedicalPrescriptionOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.realEstateContractProduct.name -> {
                            handleRealEstateContractOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.vehicleAuthenticityProductByBarCode.name -> {
                            handleVehicleAuthenticityOutput(
                                ayanActivity = activity,
                                input = input,
                                payStatus = payStatus,
                                serviceName = Products.vehicleAuthenticityProductByBarCode.name
                            )
                        }

                        Products.vehicleAuthenticityProductByDocumentNumber.name -> {
                            handleVehicleAuthenticityOutput(
                                ayanActivity = activity,
                                input = input,
                                payStatus = payStatus,
                                serviceName = Products.vehicleAuthenticityProductByBarCode.name
                            )
                        }

                        Products.telecomRegisteredLinesProduct.name -> {
                            handleTelecomRegisteredLinesOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.livelihoodInformationProduct.name -> {
                            handleLivelihoodInformationOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }

                        Products.insurancePoliciesProduct.name -> {
                            handleInsurancePoliciesOutput(
                                ayanActivity = activity, input = input, payStatus = payStatus
                            )
                        }*/

    }

    fun invoicePayment(
        callBack: String,
        gateway: String,
        purchaseKey: String,
        useCredit: Boolean,
    ) {

        PishkhanSDK.coreApi.simpleCallInvoicePayment(
            InvoicePayment.Input(
                Callback = callBack,
                Gateway = gateway,
                PurchaseKey = purchaseKey,
                UseCredit = useCredit
            )
        ) {
            it?.let {
                if (it.RedirectLink.isNotNull()) {
                    //online payment
                    it.RedirectLink?.openUrl(PishkhanSDK.whyGoogleActivity)
                }
            }
        }
    }

    fun getInvoiceInfo(
        purchaseKey: String,
        invoiceInfoOutputCallback: (InvoiceInfo.Output) -> Unit
    ) {
        PishkhanSDK.coreApi.simpleCallInvoiceInfo(input = InvoiceInfo.Input(PurchaseKey = purchaseKey)) {
            it?.let {
                invoiceInfoOutputCallback(it)
            }
        }
    }
}