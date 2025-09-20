package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.BILLS_PAYMENT)
class BillsPayment {
    data class Input(
        val Bills: List<String>,
        val Callback: String,
        val Gateway: String
    )

    data class Output(
        val PaymentKey: String,
        val RedirectLink: String?,
        val WebView: Boolean
    )
}