package ir.ayantech.pishkhansdk.mdoel

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.EndPoints

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