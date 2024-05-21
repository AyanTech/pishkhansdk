package ir.ayantech.pishkhansdk.helper

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.ChangeStatusCallback
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.ayannetworking.api.SimpleCallback
import ir.ayantech.ayannetworking.api.SuccessCallback
import ir.ayantech.networking.callBillsInfo
import ir.ayantech.networking.callUserServiceQueries
import ir.ayantech.networking.simpleCallLoginByOTP
import ir.ayantech.networking.simpleCallUserServiceQueries
import ir.ayantech.networking.simpleCallUserServiceQueryBookmark
import ir.ayantech.networking.simpleCallUserServiceQueryDelete
import ir.ayantech.networking.simpleCallUserServiceQueryNote
import ir.ayantech.networking.simpleCallUserTransactions
import ir.ayantech.pishkhansdk.Initializer
import ir.ayantech.pishkhansdk.PishkhanUser
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.model.api.BillsInfo
import ir.ayantech.pishkhansdk.model.api.LoginByOTP
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
import ir.ayantech.pishkhansdk.model.app_logic.OTP
import ir.ayantech.pishkhansdk.model.app_logic.ProductItemDetail
import ir.ayantech.pishkhansdk.ui.adapter.InquiryHistoryAdapter
import ir.ayantech.pishkhansdk.ui.adapter.TransactionAdapter
import ir.ayantech.pishkhansdk.ui.bottom_sheet.ConfirmationBottomSheet
import ir.ayantech.pishkhansdk.ui.bottom_sheet.EditInquiryHistoryBottomSheet
import ir.ayantech.pishkhansdk.ui.bottom_sheet.OtpBottomSheetDialog
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.BooleanCallBack
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.isNull
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.verticalSetup
import kotlinx.coroutines.NonCancellable.start

object PishkhanSDK {
    lateinit var coreApi: AyanApi
    lateinit var serviceApi: AyanApi
    lateinit var serviceName: String
    lateinit var whyGoogleActivity: WhyGoogleActivity<*>

    fun initialize(
        context: Context,
        schema: String,
        host: String,
        corePishkhan24Api: AyanApi,
        servicesPishkhan24Api: AyanApi,
    ) {
        PishkhanUser.context = context
        PishkhanUser.schema = schema
        PishkhanUser.host = host
        PishkhanUser.prefix = "${PishkhanUser.schema}://${PishkhanUser.host}?"
        coreApi = corePishkhan24Api
        serviceApi = servicesPishkhan24Api
    }

    fun handleUserSession(
        application: String,
        origin: String,
        platform: String,
        activity: WhyGoogleActivity<*>,
        version: String,
        successCallback: SimpleCallback?
    ) {
        whyGoogleActivity = activity

        if (PishkhanUser.token.isEmpty()) Initializer.deviceRegister(
            application = application,
            origin = origin,
            platform = platform,
            version = version,
            corePishkhan24Api = coreApi,
            successCallback = successCallback
        )
        else Initializer.updateUserSessions(
            corePishkhan24Api = coreApi,
            origin = origin,
            version = version,
        ) {
            successCallback?.invoke()
        }
    }

    fun getPishkhanToken(): String = PishkhanUser.token
    fun onInquiryButtonClicked(
        product: String,
        inputModel: BaseInputModel,
        failureCallBack: FailureCallback? = null,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        serviceName = product

        PaymentHelper.invoiceRegister(inputModel = inputModel, failureCallBack = {
            failureCallBack?.invoke(it)
        }, handleResultCallback = {
            handleResultCallback?.invoke(it)
        })
    }


    fun userPaymentIsSuccessful(
        intent: Intent,
        handleResultCallback: ((output: BaseResultModel<*>, serviceName: String) -> Unit)? = null
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
                intent = intent,
            ) {
                handleResultCallback?.invoke(
                    it, items.firstOrNull { it.startsWith("serviceName") }?.split("=")?.get(1) ?: ""
                )
            }
        }
    }

    private fun handleIntent(
        callbackDataModel: CallbackDataModel,
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
                        HandleOutput.handleOutputResult(
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
                                HandleOutput.handleOutputResult(
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
        product: String,
        context: Context,
        inquiryHistoryRv: RecyclerView,
        hasInquiryHistory: BooleanCallBack,
        handleInquiryHistoryClick: ((inputList: List<ExtraInfo>) -> Unit)? = null,
        failureCallBack: FailureCallback?,
        changeStatusCallback: ChangeStatusCallback?
    ) {
        serviceName = product
        coreApi.callUserServiceQueries(input = UserServiceQueries.Input(Service = serviceName)) {
            useCommonFailureCallback = false
            useCommonChangeStatusCallback = false
            success { historyItems ->
                if (historyItems != null) {
                    hasInquiryHistory(true)
                    inquiryHistoryRv.verticalSetup()
                    inquiryHistoryRv.adapter =
                        InquiryHistoryAdapter(historyItems) { item, viewId, position ->
                            item?.let { inquiryHistoryItem ->
                                when (viewId) {
                                    R.id.inquiryHistoryRl -> {
                                        handleInquiryHistoryClick?.invoke(inquiryHistoryItem.Parameters)
                                    }

                                    R.id.deleteIv -> {
                                        ConfirmationBottomSheet(context = context,
                                            onConfirmClicked = {
                                                ((inquiryHistoryRv.adapter as InquiryHistoryAdapter).items as ArrayList).removeAt(
                                                    position
                                                )
                                                inquiryHistoryRv.adapter?.notifyItemRemoved(position)

                                                coreApi.simpleCallUserServiceQueryDelete(
                                                    UserServiceQueryDelete.Input(
                                                        QueryUniqueID = inquiryHistoryItem.UniqueID!!
                                                    )
                                                ) {
                                                    hasInquiryHistory(historyItems.isNotEmpty())
                                                    inquiryHistoryRv.adapter?.notifyItemChanged(
                                                        position
                                                    )
                                                }
                                            }).show()
                                    }

                                    R.id.editIv -> {
                                        EditInquiryHistoryBottomSheet(context = context,
                                            note = inquiryHistoryItem.Note,
                                            onConfirmClicked = {
                                                coreApi.simpleCallUserServiceQueryNote(
                                                    UserServiceQueryNote.Input(
                                                        Note = it,
                                                        QueryUniqueID = inquiryHistoryItem.UniqueID!!
                                                    )
                                                ) {
                                                    historyItems[position].Note = it
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
                                                serviceName,
                                                context,
                                                inquiryHistoryRv,
                                                hasInquiryHistory,
                                                handleInquiryHistoryClick,
                                                failureCallBack,
                                                changeStatusCallback
                                            )
                                        }
                                    }
                                }
                            }
                        }
                } else {
                    hasInquiryHistory(false)
                }
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
            failure {
                failureCallBack?.invoke(it)
            }
        }
    }

    private fun bookmarkInquiryHistory(
        inquiryHistoryItem: UserServiceQueries.InquiryHistory, successCallback: SimpleCallback
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
        showingIcon :Boolean = false,
        serviceName: String? = null,
        userTransactionHistoryRv: RecyclerView,
        hasTransactionHistory: BooleanCallBack,
        transactionsInfoCallback: ((totalItem: Int, totalAmount: Long) -> Unit)?,
        onTransactionItemClicked: ((output: BaseResultModel<*>, serviceName: String) -> Unit)?
    ) {
        coreApi.simpleCallUserTransactions { userTransactionsOutput ->
            if (!userTransactionsOutput?.Transactions.isNullOrEmpty()) {
                 userTransactionsOutput?.Transactions?.let { transactionList ->
                    var list: List<UserTransactions.Transaction> = transactionList

                    if (serviceName.isNotNull())
                        if (serviceName == ProductItemDetail.InquiryTrafficFinesCar || serviceName == ProductItemDetail.InquiryTrafficFinesCarSummary) {
                            list =
                                transactionList.filter { it.Type.Name == ProductItemDetail.InquiryTrafficFinesCar || it.Type.Name == ProductItemDetail.InquiryTrafficFinesCarSummary }
                        } else if (serviceName == ProductItemDetail.InquiryTrafficFinesMotorcycle || serviceName == ProductItemDetail.InquiryTrafficFinesMotorcycleSummary) {
                            list =
                                transactionList.filter { it.Type.Name == ProductItemDetail.InquiryTrafficFinesMotorcycle || it.Type.Name == ProductItemDetail.InquiryTrafficFinesMotorcycleSummary }

                        } else {
                            list = transactionList.filter { it.Type.Name == serviceName }
                        }

                    val totalItem = list.size
                    val totalAmount = list.sumOf { it.Amount }
                    hasTransactionHistory(list.isNotEmpty())
                    transactionsInfoCallback?.invoke(totalItem, totalAmount)
                    setupAdapter(
                        showingIcon = showingIcon,
                        list = list,
                        transactionRv = userTransactionHistoryRv,
                    ) { output, serviceName ->
                        onTransactionItemClicked?.invoke(output, serviceName)
                    }
                }
            } else {
                hasTransactionHistory(false)
            }
        }
    }

    private fun setupAdapter(
        showingIcon:Boolean = false,
        list: List<UserTransactions.Transaction>,
        transactionRv: RecyclerView,
        onTransactionItemClicked: ((output: BaseResultModel<*>, serviceNAme: String) -> Unit)?
    ) {

        transactionRv.verticalSetup()
        transactionRv.adapter = TransactionAdapter(showingIcon,list) { item, viewId, position ->
            item?.let {
                if (it.Reference?.startsWith("http") == true) {
                    it.Reference.openUrl(whyGoogleActivity)
                } else {
                    it.Reference?.replace(PishkhanUser.prefix, "")?.split("&")?.let {
                        it.firstOrNull { it.startsWith("purchaseKey") }?.split("=")?.get(1)
                            ?.let { purchaseKey ->
                                PaymentHelper.getInvoiceInfo(
                                    purchaseKey = purchaseKey
                                ) { invoiceInfoOutput ->
                                    HandleOutput.handleOutputResult(
                                        invoiceInfoOutput = invoiceInfoOutput,
                                    ) {
                                        onTransactionItemClicked?.invoke(
                                            it, invoiceInfoOutput.Invoice.Service.Type.Name
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        }

    }

    fun login(
        phoneNumber: String,
        otp: String? = null,
        loginIsSuccessful: SuccessCallback<LoginByOTP.Output>? = null,
        confirmOtpIsSuccessful: SuccessCallback<LoginByOTP.Output>? = null,
    ) {
        coreApi.simpleCallLoginByOTP(
            input = LoginByOTP.Input(
                OTPCode = otp, Username = phoneNumber
            )
        ) { output ->
            output?.let {
                if (it.OTP.isNotNull()) {
                    loginIsSuccessful?.invoke(output)
                } else {
                    PishkhanUser.isUserSubscribed = true
                    PishkhanUser.phoneNumber = phoneNumber
                    confirmOtpIsSuccessful?.invoke(output)
                }
            }
        }
    }

    fun logout(successfulLogout: SimpleCallback) {
        PishkhanUser.token = ""
        PishkhanUser.phoneNumber = ""
        PishkhanUser.isUserSubscribed = false
        successfulLogout()
    }
}