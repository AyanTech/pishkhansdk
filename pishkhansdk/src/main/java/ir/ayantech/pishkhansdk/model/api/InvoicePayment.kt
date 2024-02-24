package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.InvoicePayment)
class InvoicePayment {
    data class Input(
        val Callback: String,
        val Gateway: String,
        val PurchaseKey: String,
        val UseCredit: Boolean
    )

    data class Output(
        val PaymentKey: String,
        val RedirectLink: String?,
        val WebView: Boolean
    )
}