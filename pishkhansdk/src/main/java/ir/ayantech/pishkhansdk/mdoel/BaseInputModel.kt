package ir.ayantech.pishkhan24.model.api

open class BaseInputModel(
    var OTPCode: String?,
    var PurchaseKey: String?,
    var TaxGroup: String? = null,
)