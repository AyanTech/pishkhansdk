package ir.ayantech.pishkhansdk

import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.pishkhan24.model.api.BaseInputModel
import ir.ayantech.pishkhansdk.enums.PrerequisitesType

import ir.ayantech.pishkhansdk.mdoel.InvoiceRegister
import ir.ayantech.pishkhansdk.mdoel.OTP
import ir.ayantech.pishkhansdk.ui.dialogs.OtpDialog
import ir.ayantech.pishkhansdk.ui.dialogs.PreviewDialog
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.fromJsonToObject
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
        failureCallBack: FailureCallback? = null
    ) {

        servicesPishkhan24Api.ayanCall<InvoiceRegister.Output>(
            AyanCallStatus {
                success {
                    it.response?.Parameters?.let { output ->
                        when (output.Prerequisites) {
                            //Service doesn't have any prerequisites so should check whether it has a preview or not
                            null -> {
                                if (output.PaymentChannels != null) {

                                    if (output.TermsAndConditions == null && output.Invoice.Service.Summary == null) {
                                        //show payment url page
                                        openPaymentUrl(activity, output, serviceName)
                                    } else {
                                        //show preview dialog with amount like 2600 or 5200
                                        showPreviewDialog(
                                            activity, serviceName, inputModel, output, true
                                        )


                                    }

                                } else {

                                    if (output.TermsAndConditions != null) {
                                        showPreviewDialog(
                                            activity, serviceName, inputModel, output, false
                                        )
                                    } else {
                                        doProperServiceCall(
                                            activity,
                                            serviceName,
                                            inputModel,
                                            output.Invoice.PurchaseKey,
                                        )
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
                                                    servicesPishkhan24Api = servicesPishkhan24Api
                                                )
                                            } else {
                                                //call invoice register again and put user entered otp to service input
                                                invoiceRegister(
                                                    activity = activity,
                                                    servicesPishkhan24Api = servicesPishkhan24Api,
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

    private fun openPaymentUrl(
        activity: WhyGoogleActivity<*>,
        output: InvoiceRegister.Output,
        serviceName: String
    ) {

    }

    private fun showPreviewDialog(
        activity: WhyGoogleActivity<*>,
        serviceName: String,
        inputModel: BaseInputModel,
        output: InvoiceRegister.Output,
        showAmountSection: Boolean
    ) {
        PreviewDialog(
            context = activity, invoiceOutput = output, showAmountSection = showAmountSection
        ) {
            if (showAmountSection) {
                openPaymentUrl(activity, output, serviceName)
            } else {
                doProperServiceCall(activity, serviceName, inputModel, output.Invoice.PurchaseKey)
            }
        }.show()
    }

    private fun doProperServiceCall(
        activity: WhyGoogleActivity<*>,
        serviceName: String,
        inputModel: BaseInputModel,
        purchaseKey: String
    ) {

    }

}