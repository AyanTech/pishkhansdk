package ir.ayantech.pishkhansdk.helper

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.ayannetworking.api.SimpleCallback
import ir.ayantech.networking.callUserServiceQueries
import ir.ayantech.networking.simpleCallUserServiceQueries
import ir.ayantech.networking.simpleCallUserServiceQueryNote
import ir.ayantech.pishkhansdk.Initializer
import ir.ayantech.pishkhansdk.PishkhanUser
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.model.api.UserServiceQueries
import ir.ayantech.pishkhansdk.model.api.UserServiceQueryNote
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.constants.Constant
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.ui.adapter.InquiryHistoryAdapter
import ir.ayantech.pishkhansdk.ui.bottom_sheet.EditInquiryHistoryBottomSheet
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.adapter.MultiViewTypeViewHolder
import ir.ayantech.whygoogle.helper.isNull
import ir.ayantech.whygoogle.helper.verticalSetup
import kotlin.math.log

object PishkhanSDK {
    fun initialize(
        context: Context,
        Application: String,
        Origin: String,
        Platform: String,
        Version: String,
        schema: String,
        host: String,
        corePishkhan24Api: AyanApi,
        successCallback: SimpleCallback?
    ) {
        PishkhanUser.context = context
        PishkhanUser.schema = schema
        PishkhanUser.host = host

        Initializer.deviceRegister(
            Application = Application,
            Origin = Origin,
            Platform = Platform,
            Version = Version,
            corePishkhan24Api = corePishkhan24Api,
            successCallback = successCallback
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
            val prefix = "${PishkhanUser.schema}://${PishkhanUser.host}?"
            val queryString = it.replace(prefix, "")
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

    private fun handleIntent(
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

    fun getInquiryHistory(
        context: Context,
        corePishkhan24Api: AyanApi, serviceName: String,
        inquiryHistoryRv: RecyclerView,
        handleInquiryHistoryClick: (List<ExtraInfo>) -> Unit
    ) {
        corePishkhan24Api.callUserServiceQueries(input = UserServiceQueries.Input(Service = serviceName)) {
            success {
                if (it != null) {
                    inquiryHistoryRv.verticalSetup()
                    inquiryHistoryRv.adapter =
                        InquiryHistoryAdapter(it) { item, viewId, position ->
                            item?.let { inquiryHistoryItem ->
                                when (viewId) {
                                    R.id.inquiryHistoryRl -> {
                                        handleInquiryHistoryClick(inquiryHistoryItem.Parameters)
                                        //inquiryBtn.performClick()

                                    }

                                    R.id.deleteIv -> {
                                        /*               DeleteInquiryHistoryDialog(
                                                           this@BaseInputFragment, product, inquiryHistoryItem
                                                       ) {
                                                           ((inquiryHistoryRv.adapter as InquiryHistoryAdapter).items as ArrayList).removeAt(
                                                               position
                                                           )
                                                           inquiryHistoryRv.adapter?.notifyItemRemoved(position)
                                                           (inquiryHistoryRv.adapter as InquiryHistoryAdapter).items.let {
                                                               noInquiryHistoryTv.changeVisibility(it.isEmpty())
                                                           }
                                                       }.show()*/
                                    }

                                    R.id.editIv -> {
                                        EditInquiryHistoryBottomSheet(
                                            context = context,
                                            note = inquiryHistoryItem.Note,
                                            onConfirmClicked = {
                                                corePishkhan24Api.simpleCallUserServiceQueryNote(
                                                    UserServiceQueryNote.Input(
                                                        Note = it,
                                                        QueryUniqueID = inquiryHistoryItem.UniqueID!!
                                                    )
                                                ) {
                                                    inquiryHistoryRv.adapter?.notifyItemChanged(
                                                        position
                                                    )

                                                }

                                            }).show()
                                    }

                                    R.id.favoriteIv -> {

                                    }
                                }
                            }
                        }
                }
            }
            failure {
                Log.d("kdhfsgv", it.toString())
            }
        }
    }

}