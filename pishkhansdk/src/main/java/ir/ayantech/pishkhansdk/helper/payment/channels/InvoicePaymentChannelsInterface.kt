package ir.ayantech.pishkhansdk.helper.payment.channels

import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.ChangeStatusCallback
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.ayannetworking.api.SuccessCallback
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.model.api.InvoicePayment
import ir.ayantech.pishkhansdk.model.api.UserInvoicePaymentSummary
import ir.ayantech.pishkhansdk.model.api.WalletPayment
import ir.ayantech.pishkhansdk.model.constants.EndPoints

interface InvoicePaymentChannelsInterface {

    val ayanApi: AyanApi
        get() = PishkhanSDK.coreApi

    fun callInvoicePayment(
        invoicePaymentInput: InvoicePayment.Input,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onInvoicePaymentOutputSuccess: (invoicePaymentOutput: InvoicePayment.Output?) -> Unit
    ) {
        ayanApi.call<InvoicePayment.Output>(
            endPoint = EndPoints.InvoicePayment,
            input = invoicePaymentInput
        ) {
            success(onInvoicePaymentOutputSuccess)
            failure {
                failureCallback?.invoke(it)
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
        }
    }

    fun payInvoiceViaWallet(
        paymentKey: String,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onWalletPaymentSuccessCallback: SuccessCallback<Void>
    ) {
        ayanApi.call<Void>(
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
        userInvoicePaymentSummaryInput: UserInvoicePaymentSummary.Input,
        failureCallback: FailureCallback? = null,
        changeStatusCallback: ChangeStatusCallback? = null,
        onUserInvoicePaymentSummaryReceived: (userInvoicePaymentSummaryOutput: UserInvoicePaymentSummary.Output?) -> Unit
    ) {
        ayanApi.call<UserInvoicePaymentSummary.Output>(
            endPoint = EndPoints.UserInvoicePaymentSummary,
            input = userInvoicePaymentSummaryInput
        ) {
            success(onUserInvoicePaymentSummaryReceived)
            failure {
                failureCallback?.invoke(it)
            }
            changeStatusCallback {
                changeStatusCallback?.invoke(it)
            }
        }
    }
}