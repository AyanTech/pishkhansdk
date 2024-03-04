package ir.ayantech.pishkhansdk.helper

import android.content.Context
import android.content.Intent
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.ayannetworking.api.SimpleCallback
import ir.ayantech.pishkhansdk.Initializer
import ir.ayantech.pishkhansdk.PishkhanUser
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.constants.Constant
import ir.ayantech.pishkhansdk.model.constants.Constant.PREFIX
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull

object PishkhanSDK {
    fun initialize(
        context: Context,
        Application: String,
        Origin: String,
        Platform: String,
        Version: String,
        corePishkhan24Api: AyanApi,
        successCallback: SimpleCallback?
    ) {
        PishkhanUser.context = context

        Initializer.deviceRegister(
            Application = Application,
            Origin = Origin,
            Platform = Platform,
            Version = Version,
            corePishkhan24Api = corePishkhan24Api,
            successCallback= successCallback
        )
    }

    fun getPishkhanToken(): String = PishkhanUser.token
    fun onInquiryButtonClicked(
        activity: WhyGoogleActivity<*>,
        inputModel: BaseInputModel,
        serviceName: String,
        servicesPishkhan24Api: AyanApi,
        corePishkhan24Api: AyanApi,
        failureCallBack: FailureCallback? = null,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PaymentHelper.invoiceRegister(activity = activity,
            inputModel = inputModel,
            serviceName = serviceName,
            servicesPishkhan24Api = servicesPishkhan24Api,
            corePishkhan24Api = corePishkhan24Api,
            failureCallBack = {
                failureCallBack?.invoke(it)
            },
            handleResultCallback = {
                handleResultCallback?.invoke(it)
            })
    }

    fun userPaymentIsSuccessful(
        activity: WhyGoogleActivity<*>,
        intent: Intent,
        corePishkhan24Api: AyanApi,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {

        intent.data?.toString()?.let {
            val queryString = it.replace(PREFIX, "")
            val items = queryString.split("&")

            val callbackDataModel = CallbackDataModel(
                sourcePage = items.firstOrNull { it.startsWith("sourcePage") }?.split("=")?.get(1),
                purchaseKey = items.firstOrNull { it.startsWith("purchaseKey") }?.split("=")
                    ?.get(1),
                paymentStatus = items.firstOrNull { it.startsWith("paymentStatus") }?.split("=")
                    ?.get(1),
                selectedGateway = items.firstOrNull { it.startsWith("selectedGateway") }?.split("=")
                    ?.get(1),
                serviceName = items.firstOrNull { it.startsWith("serviceName") }?.split("=")
                    ?.get(1),
                useCredit = items.firstOrNull { it.startsWith("useCredit") }?.split("=")?.get(1),
                voucherCode = items.firstOrNull { it.startsWith("voucherCode") }?.split("=")
                    ?.get(1),
                channelName = items.firstOrNull { it.startsWith("channelName") }?.split("=")
                    ?.get(1),
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
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        if (intent.data?.toString()?.startsWith(Constant.DEEP_LINK_PREFIX) == true) {
            if (callbackDataModel.purchaseKey != null) {
                //it means that it was a inquiry with cost
                //now should check user has paid with wallet or online
                PaymentHelper.getInvoiceInfo(
                    corePishkhan24Api = corePishkhan24Api,
                    purchaseKey = callbackDataModel.purchaseKey!!
                ) { invoiceInfoOutput ->
                    //Payment has been successfully so invoice result is checking to call service api, invoiceInfoOutput is passing for service api call
                    //service is free
                    if (invoiceInfoOutput.PaymentChannels.isNull()) {
                        HandleOutput.handleOutputResult(activity = activity,
                            invoiceInfoOutput = invoiceInfoOutput,
                            servicesPishkhan24Api = servicesPishkhan24Api,
                            handleResultCallback = {
                                handleResultCallback?.invoke(it)
                            })

                    } else {
                        if (callbackDataModel.selectedGateway != null) {
                            //means that user wants to pay with wallet

                        } else {
                            //means that user has paid online
                            if (callbackDataModel.paymentStatus == Constant.paid || callbackDataModel.paymentStatus == Constant.Settle) {
                                //should call service result api and show result page
                                HandleOutput.handleOutputResult(activity = activity,
                                    invoiceInfoOutput = invoiceInfoOutput,
                                    servicesPishkhan24Api = servicesPishkhan24Api,
                                    handleResultCallback = {
                                        handleResultCallback?.invoke(it)
                                    })
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
}