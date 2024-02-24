package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.app_logic.Action
import ir.ayantech.pishkhansdk.model.app_logic.Invoice
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel

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