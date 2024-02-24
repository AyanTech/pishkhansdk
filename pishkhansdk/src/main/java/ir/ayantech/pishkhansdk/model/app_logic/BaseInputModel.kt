package ir.ayantech.pishkhansdk.model.app_logic

open class BaseInputModel(
    var OTPCode: String?,
    var PurchaseKey: String?,
    var TaxGroup: String? = null,
)