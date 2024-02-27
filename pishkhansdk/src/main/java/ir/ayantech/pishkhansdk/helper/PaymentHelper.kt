package ir.ayantech.pishkhansdk.helper

import ir.ayantech.ayannetworking.api.AyanApi
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
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleTrafficFinesCarNoDetailsOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleTrafficFinesCarOutput
import ir.ayantech.pishkhansdk.model.api.InvoiceInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.pishkhansdk.ui.dialogs.OtpDialog
import ir.ayantech.pishkhansdk.ui.dialogs.PreviewDialog
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.fromJsonToObject
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.toJsonString

object PaymentHelper {

    var otpDialog: OtpDialog? = null

    /**
     * This method checks if the service has a prerequisite
     **/

    fun invoiceRegister(
        activity: WhyGoogleActivity<*>,
        inputModel: BaseInputModel,
        serviceName: String,
        servicesPishkhan24Api: AyanApi,
        corePishkhan24Api: AyanApi,
        failureCallBack: FailureCallback? = null,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {

        servicesPishkhan24Api.ayanCall<InvoiceRegister.Output>(
            AyanCallStatus {
                success {
                    otpDialog?.dismiss()
                    it.response?.Parameters?.let { output ->
                        when (output.Prerequisites) {
                            //Service doesn't have any prerequisites so should check whether it has a preview or not
                            null -> {
                                if (output.PaymentChannels != null) {

                                    if (output.TermsAndConditions == null && output.Invoice.Service.Summary == null) {
                                        //show payment url page
                                        openOnlinePaymentUrl(
                                            activity = activity,
                                            invoiceOutput = output,
                                            corePishkhan24Api = corePishkhan24Api
                                        )
                                    } else {
                                        //show preview dialog with amount like 2600 or 5200
                                        showPreviewDialog(
                                            activity = activity,
                                            serviceName = serviceName,
                                            inputModel = inputModel,
                                            output = output,
                                            showAmountSection = true,
                                            corePishkhan24Api = corePishkhan24Api,
                                            servicesPishkhan24Api = servicesPishkhan24Api
                                        )

                                    }

                                } else {

                                    if (output.TermsAndConditions != null) {
                                        showPreviewDialog(
                                            activity = activity,
                                            serviceName = serviceName,
                                            inputModel = inputModel,
                                            output = output,
                                            showAmountSection = false,
                                            corePishkhan24Api = corePishkhan24Api,
                                            servicesPishkhan24Api = servicesPishkhan24Api
                                        )
                                    } else {
                                        doProperServiceCall(
                                            activity = activity,
                                            serviceName = serviceName,
                                            inputModel = inputModel,
                                            purchaseKey = output.Invoice.PurchaseKey,
                                            servicesPishkhan24Api = servicesPishkhan24Api
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
                                        otpDialog = OtpDialog(
                                            context = activity,
                                            otp = output.Prerequisites.Value?.fromJsonToObject<OTP>(),
                                        ) { otpCode ->
                                            //if otp code was null,it means that user has clicked retryTv
                                            if (otpCode == null) {
                                                invoiceRegister(
                                                    activity = activity,
                                                    inputModel = inputModel,
                                                    serviceName = serviceName,
                                                    servicesPishkhan24Api = servicesPishkhan24Api,
                                                    corePishkhan24Api = corePishkhan24Api
                                                )
                                            } else {
                                                //call invoice register again and put user entered otp to service input
                                                invoiceRegister(
                                                    activity = activity,
                                                    servicesPishkhan24Api = servicesPishkhan24Api,
                                                    corePishkhan24Api = corePishkhan24Api,
                                                    inputModel = inputModel.also {
                                                        it.OTPCode = otpCode
                                                    },
                                                    serviceName = serviceName,
                                                )
                                            }
                                        }
                                        otpDialog?.show()
                                    }
                                }
                            }
                        }
                    }
                }
                failure {
                    if (otpDialog?.isShowing == true) {
                        otpDialog?.setError(it.failureMessage)
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
        activity: WhyGoogleActivity<*>,
        invoiceOutput: InvoiceRegister.Output,
        corePishkhan24Api: AyanApi
    ) {
        invoiceOutput.PaymentChannels?.get(1)?.Gateways?.let { gateways ->
            invoicePayment(
                activity = activity,
                callBack = CallbackDataModel(
                    sourcePage = "factor",
                    purchaseKey = invoiceOutput.Invoice.PurchaseKey,
                    paymentStatus = "#status#",
                    selectedGateway = null,
                    useCredit = "false",
                    serviceName = invoiceOutput.Invoice.Service.Type.Name,
                    channelName = invoiceOutput.PaymentChannels[1].Type.Name
                ).createCallBackLink(),
                gateway = gateways[0].Type.Name,
                purchaseKey = invoiceOutput.Invoice.PurchaseKey,
                useCredit = false,
                corePishkhan24Api = corePishkhan24Api
            )
        }
    }

    private fun showPreviewDialog(
        activity: WhyGoogleActivity<*>,
        serviceName: String,
        inputModel: BaseInputModel,
        output: InvoiceRegister.Output,
        showAmountSection: Boolean,
        corePishkhan24Api: AyanApi,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PreviewDialog(
            context = activity, invoiceOutput = output, showAmountSection = showAmountSection
        ) {
            if (showAmountSection) {
                openOnlinePaymentUrl(
                    activity = activity,
                    invoiceOutput = output,
                    corePishkhan24Api = corePishkhan24Api
                )
            } else {
                doProperServiceCall(
                    activity = activity,
                    serviceName = serviceName,
                    inputModel = inputModel,
                    purchaseKey = output.Invoice.PurchaseKey,
                    servicesPishkhan24Api = servicesPishkhan24Api
                ) {
                    handleResultCallback?.invoke(it)
                }
            }
        }.show()
    }

    private fun doProperServiceCall(
        activity: WhyGoogleActivity<*>,
        serviceName: String,
        inputModel: BaseInputModel,
        purchaseKey: String,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        inputModel.also { it.PurchaseKey = purchaseKey }.let { input ->
            when (serviceName) {
                Products.justiceSharesProduct.name -> {
                    handleJusticeSharesPortfolioOutput(
                        activity = activity,
                        input = input,
                        servicesPishkhan24Api = servicesPishkhan24Api,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.subventionHistoryProduct.name -> {
                    handleSubventionHistoryOutput(
                        activity = activity,
                        input = input,
                        servicesPishkhan24Api = servicesPishkhan24Api,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.carTrafficFinesProduct.name -> {
                    handleTrafficFinesCarOutput(
                        activity = activity,
                        input = input,
                        servicesPishkhan24Api = servicesPishkhan24Api,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.carTrafficFinesNoDetailsProduct.name -> {
                    handleTrafficFinesCarNoDetailsOutput(
                        activity = activity,
                        input = input,
                        servicesPishkhan24Api = servicesPishkhan24Api,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.motorTrafficFinesProduct.name -> {

                }

                Products.motorTrafficFinesSummeryProduct.name -> {

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
        activity: WhyGoogleActivity<*>,
        callBack: String,
        gateway: String,
        purchaseKey: String,
        useCredit: Boolean,
        corePishkhan24Api: AyanApi
    ) {

        corePishkhan24Api.simpleCallInvoicePayment(
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
                    it.RedirectLink?.openUrl(activity)
                }
            }
        }
    }

    fun getInvoiceInfo(
        corePishkhan24Api: AyanApi,
        purchaseKey: String,
        invoiceInfoOutputCallback: (InvoiceInfo.Output) -> Unit
    ) {
        corePishkhan24Api.simpleCallInvoiceInfo(input = InvoiceInfo.Input(PurchaseKey = purchaseKey)) {
            it?.let {
                invoiceInfoOutputCallback(it)
            }
        }
    }
}