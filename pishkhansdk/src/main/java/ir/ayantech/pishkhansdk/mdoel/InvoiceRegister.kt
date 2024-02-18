package ir.ayantech.pishkhansdk.mdoel

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.EndPoints

@AyanAPI(endPoint = EndPoints.InvoiceRegister)
class InvoiceRegister {
    data class Input(
        val Parameters: String,
        val Service: String
    )

    data class Output(
        val Credit: Long,
        val Invoice: Invoice,
        val PaymentChannels: List<PaymentChannel>?,
        val Prerequisites: Action?,
        val TermsAndConditions: String?
    )
}