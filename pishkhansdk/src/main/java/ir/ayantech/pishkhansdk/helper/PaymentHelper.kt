package ir.ayantech.pishkhansdk.helper

import android.os.Build.VERSION_CODES.P
import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.networking.simpleCallInvoiceInfo
import ir.ayantech.networking.simpleCallInvoicePayment
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleAccountNumberByIbanOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleBankChequeStatusSayadOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleIbanByAccountNumberOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleIbanByCardNumberOutput
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.enums.PrerequisitesType
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.api.InvoicePayment

import ir.ayantech.pishkhansdk.model.api.InvoiceRegister
import ir.ayantech.pishkhansdk.model.app_logic.OTP
import ir.ayantech.pishkhansdk.model.app_logic.createCallBackLink
import ir.ayantech.pishkhansdk.helper.HandleOutput.handleJusticeSharesPortfolioOutput
import ir.ayantech.pishkhansdk.helper.HandleOutput.handlePostPackageTrackingOutput
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
        invoiceOutput.PaymentChannels?.find { it.Type.Name == "OnlinePayment" }?.Gateways?.let { gateways ->
            invoicePayment(
                callBack = CallbackDataModel(
                    sourcePage = "factor",
                    purchaseKey = invoiceOutput.Invoice.PurchaseKey,
                    paymentStatus = "#status#",
                    selectedGateway = null,
                    useCredit = "false",
                    serviceName = invoiceOutput.Invoice.Service.Type.Name,
                    channelName = "OnlinePayment"
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

                Products.ibanByCardNumberProduct.name -> {
                    handleIbanByCardNumberOutput(
                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }


                Products.ibanByAccountNumberProduct.name -> {
                    handleIbanByAccountNumberOutput(
                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }


                Products.accountNumberByIbanProduct.name -> {
                    handleAccountNumberByIbanOutput(
                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.postPackageTrackingProduct.name -> {
                    handlePostPackageTrackingOutput(
                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }

                Products.sayadChequeProduct.name -> {
                    handleBankChequeStatusSayadOutput(
                        input = input,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                }


                else -> {}

            }
        }

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