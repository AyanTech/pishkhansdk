package ir.ayantech.pishkhansdk.helper.extensions

import android.content.Intent
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.pishkhansdk.helper.HandleOutput
import ir.ayantech.pishkhansdk.helper.PaymentHelper
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.constants.Constant
import ir.ayantech.pishkhansdk.model.constants.Constant.PREFIX
import ir.ayantech.pishkhansdk.model.constants.Parameter
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.api.JusticeSharesPortfolio
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull

fun onInquiryButtonClicked(
    activity: WhyGoogleActivity<*>,
    inputModel: BaseInputModel,
    serviceName: String,
    servicesPishkhan24Api: AyanApi,
    corePishkhan24Api: AyanApi,
    failureCallBack: FailureCallback? = null,
    handleResultCallback: ((output: JusticeSharesPortfolio.Output?) -> Unit)? = null
) {
    PaymentHelper.invoiceRegister(
        activity = activity,
        inputModel = inputModel,
        serviceName = serviceName,
        servicesPishkhan24Api = servicesPishkhan24Api,
        corePishkhan24Api = corePishkhan24Api,
        failureCallBack = {
            failureCallBack?.invoke(it)
        }, handleResultCallback = {
            handleResultCallback?.invoke(it)
        }
    )
}

fun userPaymentIsSuccessful(
    activity: WhyGoogleActivity<*>,
    intent: Intent,
    corePishkhan24Api: AyanApi,
    servicesPishkhan24Api: AyanApi,
    handleResultCallback: ((output: JusticeSharesPortfolio.Output?) -> Unit)? = null
) {

    intent.data?.toString()?.let {
        val queryString = it.replace(PREFIX, "")
        val items = queryString.split("&")

        val callbackDataModel = CallbackDataModel(
            sourcePage = items.firstOrNull { it.startsWith("sourcePage") }?.split("=")?.get(1),
            purchaseKey = items.firstOrNull { it.startsWith("purchaseKey") }?.split("=")?.get(1),
            paymentStatus = items.firstOrNull { it.startsWith("paymentStatus") }?.split("=")
                ?.get(1),
            selectedGateway = items.firstOrNull { it.startsWith("selectedGateway") }?.split("=")
                ?.get(1),
            serviceName = items.firstOrNull { it.startsWith("serviceName") }?.split("=")?.get(1),
            useCredit = items.firstOrNull { it.startsWith("useCredit") }?.split("=")?.get(1),
            voucherCode = items.firstOrNull { it.startsWith("voucherCode") }?.split("=")?.get(1),
            channelName = items.firstOrNull { it.startsWith("channelName") }?.split("=")?.get(1),
        )
        handleIntent(
            callbackDataModel = callbackDataModel,
            activity = activity,
            intent = intent,
            corePishkhan24Api = corePishkhan24Api,
            servicesPishkhan24Api = servicesPishkhan24Api
        ) {
            handleResultCallback?.invoke(it)
        }
    }
}

fun handleIntent(
    callbackDataModel: CallbackDataModel,
    activity: WhyGoogleActivity<*>,
    intent: Intent,
    corePishkhan24Api: AyanApi,
    servicesPishkhan24Api: AyanApi,
    handleResultCallback: ((output: JusticeSharesPortfolio.Output?) -> Unit)? = null
) {
    if (intent.data?.toString()?.startsWith(Constant.DEEP_LINK_PREFIX) == true) {
        if (callbackDataModel.purchaseKey != null) {
            //it means that it was a inquiry with cost
            //now should check user has paid with wallet or online
            PaymentHelper.getInvoiceInfo(
                corePishkhan24Api = corePishkhan24Api, purchaseKey = callbackDataModel.purchaseKey!!
            ) { invoiceInfoOutput ->
                //Payment has been successfully so invoice result is checking to call service api, invoiceInfoOutput is passing for service api call
                //service is free
                if (invoiceInfoOutput.PaymentChannels.isNull()) {
                    HandleOutput.handleJusticeSharesPortfolioOutput(
                        activity = activity, input = JusticeSharesPortfolio.Input(
                            NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                            OTPCode = null,
                            PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                        ), servicesPishkhan24Api = servicesPishkhan24Api,
                        handleResultCallback = {
                            handleResultCallback?.invoke(it)
                        }
                    )
                } else {
                    if (callbackDataModel.selectedGateway != null) {
                        //means that user wants to pay with wallet

                    } else {
                        //means that user has paid online
                        if (callbackDataModel.paymentStatus == Constant.paid || callbackDataModel.paymentStatus == Constant.Settle) {
                            //should call service result api and show result page
                            HandleOutput.handleJusticeSharesPortfolioOutput(
                                activity = activity, input = JusticeSharesPortfolio.Input(
                                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                                    OTPCode = null,
                                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                                ), servicesPishkhan24Api = servicesPishkhan24Api,
                                handleResultCallback = {
                                    handleResultCallback?.invoke(it)
                                }
                            )
                        } else {
                            //payment was unsuccessful
                            //should show factor to user
                            /*                   checkFactorFragmentIsNullOrNot(
                                                   invoiceInfoOutput,
                                                   callbackDataModel,
                                                   walletCharged = false
                                               )*/
                        }
                    }
                }
            }
        } else {
            //is it possible that purchase key be null?when?
            //when user pays bills
            /*      start(BillsPaymentStatusResultFragment().also {
                      it.serviceName = callbackDataModel.serviceName
                      it.paymentStatus = callbackDataModel.paymentStatus
                  })*/
        }

    }
}
