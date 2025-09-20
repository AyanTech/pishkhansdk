package ir.ayantech.pishkhansdk.model.api

data class PaymentSummary(
    val Amount: Long,
    val Cash: Long,
    val Credit: Long,
    val Fee: Long,
    val ChannelType: String,
    val GatewayType: String,
    val HasBalance: Boolean,
    val NeededCashAmount: Long,
    val TotalAmount: Long,
)