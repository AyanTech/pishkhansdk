package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserInvoicePaymentSummary)
class UserInvoicePaymentSummary {
    class Input(
        val CreditBalance: Boolean,
        val GatewayName: List<String>,
        val PurchaseKey: String?,
        val VoucherCode: String?,
    )

    class Output(val Result: List<PaymentSummary>)

}

