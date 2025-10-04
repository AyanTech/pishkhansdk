package ir.ayantech.pishkhansdk.ui.fragments

import ir.ayantech.pishkhansdk.helper.PaymentHelper
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.helper.payment.channels.BillsPaymentChannelsInterface
import ir.ayantech.pishkhansdk.model.api.BillsPayment
import ir.ayantech.pishkhansdk.model.api.BillsPaymentChannels
import ir.ayantech.pishkhansdk.model.api.UserBillsPaymentSummary
import ir.ayantech.pishkhansdk.model.app_logic.PaymentData
import ir.ayantech.pishkhansdk.model.component_data_model.PishkhansdkExtraInfoComponentDataModel
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.fromJsonToObject

open class BillsPaymentChannelsFragment(): PaymentChannelsFragment(), BillsPaymentChannelsInterface {

    private var bills: List<String>? = null
    private var service: String? = null
    private var onBillsPaid: SimpleCallBack = {}
    private var callbackUrl: String? = null

    override val paymentChannelSource: PaymentChannelSource
        get() = PaymentChannelSource.BILLS_PAYMENT

    constructor(
        bills: List<String>,
        service: String,
        callbackUrl: String,
        extraInfoComponentDataModel: PishkhansdkExtraInfoComponentDataModel? = null,
        onBillsPaid: SimpleCallBack
    ): this() {
        this.bills = bills
        this.service = service
        this.onBillsPaid = onBillsPaid
        PishkhanSDK.onBillsPaid = onBillsPaid
        PaymentHelper.extraInfoComponentDataModelForPayViaCNPG = extraInfoComponentDataModel
        this.callbackUrl = callbackUrl
    }

    override fun getPaymentChannels() {
        getBillsPaymentChannels(
            billsPaymentChannelsInput = BillsPaymentChannels.Input(Bills = bills ?: listOf(), Service = service ?: "")
        ) {
            this.paymentChannels = it?.PaymentChannels ?: listOf()
        }
    }

    override fun getPaymentChannelsSummary() {
        callUserBillsPaymentSummary(
            userBillsPaymentSummaryInput = UserBillsPaymentSummary.Input(
                BillUniqueID = bills ?: listOf(),
                GatewayName = paymentChannels.map {
                    it.Gateways?.firstOrNull()?.Type?.Name ?: ""
                }
            )
        ) {
            this.userBillsPaymentSummary = it?.Result ?: listOf()
        }
    }

    override val dataForSave: Any
        get() = billsPaymentInput

    open val billsPaymentInput: BillsPayment.Input
        get() = BillsPayment.Input(
            Bills = bills ?: listOf(),
            Callback = callbackUrl ?: "",
            Gateway = selectedPaymentChannel?.Gateways?.firstOrNull()?.Type?.Name ?: ""
        )

    override fun getPaymentData(callback: (PaymentData) -> Unit) {
        callBillPayment(
            billsPaymentInput = billsPaymentInput
        ) {
            it?.let { output ->
                callback(PaymentData(PaymentKey = output.PaymentKey, RedirectLink = output.RedirectLink))
            }
        }
    }

    override fun onWalletChargedViaCNPG(savedData: String) {
        val billsPaymentInput = savedData.fromJsonToObject<BillsPayment.Input>()
        callBillPayment(
            billsPaymentInput = billsPaymentInput
        ) {
            it?.let { output ->
                onPaymentButtonClicked(PaymentData(PaymentKey = output.PaymentKey, RedirectLink = output.RedirectLink))
            }
        }
    }

    override val onCNPGPaymentSucceeded: SimpleCallBack
        get() = onBillsPaid

    override fun callWalletPayment(paymentKey: String) {
        payBillsViaWallet(
            paymentKey = paymentKey,
        ) {
            onBillsPaid()
        }
    }

}