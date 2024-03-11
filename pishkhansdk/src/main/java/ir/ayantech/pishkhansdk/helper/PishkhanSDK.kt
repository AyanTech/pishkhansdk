package ir.ayantech.pishkhansdk.helper

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.ayannetworking.api.SimpleCallback
import ir.ayantech.networking.simpleCallUserServiceQueries
import ir.ayantech.networking.simpleCallUserServiceQueryBookmark
import ir.ayantech.networking.simpleCallUserServiceQueryDelete
import ir.ayantech.networking.simpleCallUserServiceQueryNote
import ir.ayantech.networking.simpleCallUserTransactions
import ir.ayantech.pishkhansdk.Initializer
import ir.ayantech.pishkhansdk.PishkhanUser
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.model.api.UserServiceQueries
import ir.ayantech.pishkhansdk.model.api.UserServiceQueryBookmark
import ir.ayantech.pishkhansdk.model.api.UserServiceQueryDelete
import ir.ayantech.pishkhansdk.model.api.UserServiceQueryNote
import ir.ayantech.pishkhansdk.model.api.UserTransactions
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.constants.Constant
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.ui.adapter.InquiryHistoryAdapter
import ir.ayantech.pishkhansdk.ui.adapter.TransactionAdapter
import ir.ayantech.pishkhansdk.ui.bottom_sheet.ConfirmationBottomSheet
import ir.ayantech.pishkhansdk.ui.bottom_sheet.EditInquiryHistoryBottomSheet
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.verticalSetup

object PishkhanSDK {
     lateinit var coreApi: AyanApi
     lateinit var serviceApi: AyanApi

    fun initialize(
        context: Context,
        application: String,
        origin: String,
        platform: String,
        version: String,
        schema: String,
        host: String,
        corePishkhan24Api: AyanApi,
        servicesPishkhan24Api: AyanApi,
        successCallback: SimpleCallback?
    ) {
        PishkhanUser.context = context
        PishkhanUser.schema = schema
        PishkhanUser.host = host
        coreApi = corePishkhan24Api
        serviceApi = servicesPishkhan24Api

        Initializer.updateUserSessions(
            corePishkhan24Api = corePishkhan24Api,
            origin = origin,
            version = version,
        )

        if (PishkhanUser.token.isEmpty())
            Initializer.deviceRegister(
                application = application,
                origin = origin,
                platform = platform,
                version = version,
                corePishkhan24Api = corePishkhan24Api,
                successCallback = successCallback
            )

    }

    fun getPishkhanToken(): String = PishkhanUser.token
    fun onInquiryButtonClicked(
        activity: WhyGoogleActivity<*>,
        inputModel: BaseInputModel,
        serviceName: String,
        failureCallBack: FailureCallback? = null,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PaymentHelper.invoiceRegister(activity = activity,
            inputModel = inputModel,
            serviceName = serviceName,
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
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {

        intent.data?.toString()?.let {
            val prefix = PishkhanUser.prefix
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
            ) {
                handleResultCallback?.invoke(it)
            }
        }
    }

    private fun handleIntent(
        callbackDataModel: CallbackDataModel,
        activity: WhyGoogleActivity<*>,
        intent: Intent,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        if (intent.data?.toString()?.startsWith("${PishkhanUser.schema}:") == true) {
            if (callbackDataModel.purchaseKey != null) {
                //it means that it was a inquiry with cost
                //now should check user has paid with wallet or online
                PaymentHelper.getInvoiceInfo(
                    purchaseKey = callbackDataModel.purchaseKey!!
                ) { invoiceInfoOutput ->
                    //Payment has been successfully so invoice result is checking to call service api, invoiceInfoOutput is passing for service api call
                    //service is free
                    if (invoiceInfoOutput.PaymentChannels.isNull()) {
                        HandleOutput.handleOutputResult(activity = activity,
                            invoiceInfoOutput = invoiceInfoOutput,
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
        serviceName: String,
        inquiryHistoryRv: RecyclerView,
        handleInquiryHistoryClick: ((List<ExtraInfo>) -> Unit)? = null
    ) {
        coreApi.simpleCallUserServiceQueries(input = UserServiceQueries.Input(Service = serviceName)) {
            if (it != null) {
                inquiryHistoryRv.verticalSetup()
                inquiryHistoryRv.adapter =
                    InquiryHistoryAdapter(it) { item, viewId, position ->
                        item?.let { inquiryHistoryItem ->
                            when (viewId) {
                                R.id.inquiryHistoryRl -> {
                                    handleInquiryHistoryClick?.invoke(inquiryHistoryItem.Parameters)
                                }

                                R.id.deleteIv -> {
                                    ConfirmationBottomSheet(
                                        context = context, onConfirmClicked = {
                                            ((inquiryHistoryRv.adapter as InquiryHistoryAdapter).items as ArrayList).removeAt(
                                                position
                                            )
                                            inquiryHistoryRv.adapter?.notifyItemRemoved(position)

                                            coreApi.simpleCallUserServiceQueryDelete(
                                                UserServiceQueryDelete.Input(
                                                    QueryUniqueID = inquiryHistoryItem.UniqueID!!
                                                )
                                            ) {
                                                inquiryHistoryRv.adapter?.notifyItemChanged(
                                                    position
                                                )
                                            }
                                        }).show()
                                }

                                R.id.editIv -> {
                                    EditInquiryHistoryBottomSheet(
                                        context = context,
                                        note = inquiryHistoryItem.Note,
                                        onConfirmClicked = {
                                            coreApi.simpleCallUserServiceQueryNote(
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
                                    bookmarkInquiryHistory(
                                        inquiryHistoryItem
                                    ) {
                                        getInquiryHistory(
                                            context,
                                            serviceName,
                                            inquiryHistoryRv
                                        )
                                    }
                                }
                            }
                        }
                    }
            }

        }
    }

    private fun bookmarkInquiryHistory(
        inquiryHistoryItem: UserServiceQueries.InquiryHistory,
        successCallback: SimpleCallback
    ) {
        coreApi.simpleCallUserServiceQueryBookmark(
            UserServiceQueryBookmark.Input(
                Favorite = !inquiryHistoryItem.Favorite,
                QueryUniqueID = inquiryHistoryItem.UniqueID!!
            )
        ) {
            successCallback()
        }
    }

    fun getUserTransactionHistory(
        activity: WhyGoogleActivity<*>,
        userTransactionHistoryRv: RecyclerView,
        onTransactionItemClicked: ((output: BaseResultModel<*>, serviceName: String) -> Unit)?
    ) {
        coreApi.simpleCallUserTransactions {
            it?.let {
                setupAdapter(
                    activity = activity,
                    list = it.Transactions ?: arrayListOf(),
                    transactionRv = userTransactionHistoryRv,
                ) { output, serviceName ->
                    onTransactionItemClicked?.invoke(output, serviceName)
                }
            }
        }
    }

    private fun setupAdapter(
        activity: WhyGoogleActivity<*>,
        list: List<UserTransactions.Transaction>,
        transactionRv: RecyclerView,
        onTransactionItemClicked: ((output: BaseResultModel<*>, serviceNAme: String) -> Unit)?
    ) {

        transactionRv.verticalSetup()
        transactionRv.adapter = TransactionAdapter(list)
        { item, viewId, position ->
            item?.let {
                if (it.Reference?.startsWith("http") == true) {
                    it.Reference.openUrl(activity)
                } else {
                    it.Reference?.replace(PishkhanUser.prefix, "")?.split("&")?.let {
                        it.firstOrNull { it.startsWith("purchaseKey") }?.split("=")?.get(1)
                            ?.let { purchaseKey ->
                                PaymentHelper.getInvoiceInfo(
                                    purchaseKey = purchaseKey
                                ) { invoiceInfoOutput ->
                                    HandleOutput.handleOutputResult(
                                        activity = activity,
                                        invoiceInfoOutput = invoiceInfoOutput,
                                    ) {
                                        onTransactionItemClicked?.invoke(
                                            it,
                                            invoiceInfoOutput.Invoice.Service.Type.Name
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        }

    }
}