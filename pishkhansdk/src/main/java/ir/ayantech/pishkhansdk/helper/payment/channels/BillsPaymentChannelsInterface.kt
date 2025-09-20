package ir.ayantech.pishkhansdk.helper.payment.channels

import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.ChangeStatusCallback
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.ayannetworking.api.SuccessCallback
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.model.api.BillsPayment
import ir.ayantech.pishkhansdk.model.api.BillsPaymentChannels
import ir.ayantech.pishkhansdk.model.api.UserBillsPaymentSummary
import ir.ayantech.pishkhansdk.model.api.WalletPayment
import ir.ayantech.pishkhansdk.model.constants.EndPoints


interface BillsPaymentChannelsInterface {

    val corePishkhan24AyanApi: AyanApi
        get() = PishkhanSDK.coreApi

    fun getBillsPaymentChannels(
        billsPaymentChannelsInput: BillsPaymentChannels.Input,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onBillsPaymentChannelsReceived: (billsPaymentChannelsOutput: BillsPaymentChannels.Output?) -> Unit
    ) {
        corePishkhan24AyanApi.call<BillsPaymentChannels.Output>(
            endPoint = EndPoints.BILLS_PAYMENT_CHANNELS,
            input = billsPaymentChannelsInput,
        ) {
            success {
                onBillsPaymentChannelsReceived(it)
            }
            failure {
                failureCallback?.invoke(it)
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
        }
    }

    fun callBillPayment(
        billsPaymentInput: BillsPayment.Input,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onBillsPaymentSuccess: (billsPaymentOutput: BillsPayment.Output?) -> Unit
    ) {
        corePishkhan24AyanApi.call<BillsPayment.Output>(
            endPoint = EndPoints.BILLS_PAYMENT,
            input = billsPaymentInput
        ) {
            success {
                onBillsPaymentSuccess(it)
            }
            failure {
                failureCallback?.invoke(it)
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
        }
    }

    fun payBillsViaWallet(
        paymentKey: String,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onWalletPaymentSuccessCallback: SuccessCallback<Void>
    ) {
        corePishkhan24AyanApi.call<Void>(
            endPoint = EndPoints.WALLET_PAYMENT,
            input = WalletPayment.Input(PaymentKey = paymentKey)
        ) {
            success(onWalletPaymentSuccessCallback)
            failure {
                failureCallback?.invoke(it)
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
        }
    }

    fun callUserBillsPaymentSummary(
        userBillsPaymentSummaryInput: UserBillsPaymentSummary.Input,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onUserBillsPaymentSummaryReceived: (userBillsPaymentSummaryOutput: UserBillsPaymentSummary.Output?) -> Unit
    ) {
        corePishkhan24AyanApi.call<UserBillsPaymentSummary.Output>(
            endPoint = EndPoints.USER_BILLS_PAYMENT_SUMMARY,
            input = userBillsPaymentSummaryInput
        ) {
            success {
                onUserBillsPaymentSummaryReceived(it)
            }
            failure {
                failureCallback?.invoke(it)
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
        }
    }
}