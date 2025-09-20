package ir.ayantech.pishkhansdk.ui.fragments

import ir.ayantech.pishkhansdk.helper.HandleOutput
import ir.ayantech.pishkhansdk.helper.PaymentHelper
import ir.ayantech.pishkhansdk.helper.payment.channels.InvoicePaymentChannelsInterface
import ir.ayantech.pishkhansdk.helper.payment.channels.PaymentChannels
import ir.ayantech.pishkhansdk.model.api.InvoicePayment
import ir.ayantech.pishkhansdk.model.api.UserInvoicePaymentSummary
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.pishkhansdk.model.app_logic.PaymentData
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.fromJsonToObject

open class InvoicePaymentChannelsFragment(): PaymentChannelsFragment(), InvoicePaymentChannelsInterface {

    private var invoicePaymentChannels: List<PaymentChannel>? = null
    private var purchaseKey: String? = null
    private var voucherCode: String? = null
    private var onInvoicePaid: ((output: BaseResultModel<*>) -> Unit)? = null
    private var callbackUrl: String? = null

    override val paymentChannelSource: PaymentChannelSource
        get() = PaymentChannelSource.INVOICE_PAYMENT

    override val dataForSave: Any
        get() = invoicePaymentInput

    open val invoicePaymentInput: InvoicePayment.Input
        get() = InvoicePayment.Input(
            Callback = callbackUrl ?: "",
            Gateway = selectedPaymentChannel?.Gateways?.firstOrNull()?.Type?.Name ?: "",
            PurchaseKey = purchaseKey ?: "",
            UseCredit = false
        )

    constructor(
        invoicePaymentChannels: List<PaymentChannel>,
        purchaseKey: String,
        voucherCode: String? = null,
        callbackUrl: String,
        onInvoicePaid: ((output: BaseResultModel<*>) -> Unit)?,
    ): this() {
        this.invoicePaymentChannels = invoicePaymentChannels
        this.purchaseKey = purchaseKey
        this.invoicePurchaseKey = purchaseKey
        this.voucherCode = voucherCode
        this.callbackUrl = callbackUrl
        this.onInvoicePaid = onInvoicePaid
    }

    override fun getPaymentChannels() {
        this.paymentChannels = invoicePaymentChannels ?: listOf()
    }

    override fun getPaymentChannelsSummary() {
        callUserBillsPaymentSummary(
            userInvoicePaymentSummaryInput = UserInvoicePaymentSummary.Input(
                CreditBalance = false,
                GatewayName = paymentChannels.map {
                    it.Gateways?.firstOrNull()?.Type?.Name ?: ""
                },
                PurchaseKey = purchaseKey,
                VoucherCode = voucherCode
            )
        ) {
            this.userBillsPaymentSummary = it?.Result ?: listOf()
        }
    }

    override fun getPaymentData(callback: (PaymentData) -> Unit) {
        callInvoicePayment(
            invoicePaymentInput = invoicePaymentInput
        ) {
            it?.let { output ->
                callback(PaymentData(PaymentKey = output.PaymentKey, RedirectLink = output.RedirectLink))
            }
        }
    }

    override fun onWalletChargedViaCNPG(savedData: String) {
        val invoicePaymentInput = savedData.fromJsonToObject<InvoicePayment.Input>()
        callInvoicePayment(invoicePaymentInput = invoicePaymentInput) {
            it?.let { output ->
                onPaymentButtonClicked(
                    PaymentData(
                        PaymentKey = output.PaymentKey,
                        RedirectLink = output.RedirectLink
                    )
                )
            }
        }
    }

    override fun callWalletPayment(paymentKey: String) {
        payInvoiceViaWallet(
            paymentKey = paymentKey,
        ) {
            prepareOutputResult()
        }
    }

    open fun prepareOutputResult() {
        PaymentHelper.getInvoiceInfo(
            purchaseKey = purchaseKey ?: ""
        ) { invoiceInfoOutput ->
            HandleOutput.handleOutputResult(
                invoiceInfoOutput = invoiceInfoOutput,
                handleResultCallback = {
                    onInvoicePaid?.invoke(it)
                })
        }
    }

    override val onCNPGPaymentSucceeded: SimpleCallBack
        get() = {
            prepareOutputResult()
        }
}