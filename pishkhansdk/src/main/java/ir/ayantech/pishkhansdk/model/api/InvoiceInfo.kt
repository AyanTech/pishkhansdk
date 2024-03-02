package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.app_logic.Invoice
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel

@AyanAPI(endPoint = EndPoints.InvoiceInfo)
class InvoiceInfo {
    data class Input(
        val PurchaseKey: String
    )

    data class Output(
        val Credit: Long,
        val Invoice: Invoice,
        val PaymentChannels: List<PaymentChannel>,
        val Query: Query
    )

    data class Query(
        val AutoFill: Boolean,
        val Favorite: Boolean,
        val Index: String,
        val Note: String,
        val Parameters: List<ExtraInfo>,
        val UniqueID: String
    )

    data class ExtraInfo(
        val Key: String,
        val Value: String
    )
}