package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Payment
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.TrafficFinesCar)
class TrafficFinesCar {
    class Input(
        val MobileNumber: String,
        val NationalCode: String,
        OTPCode: String?,
        val PlateNumber: String,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Messages: Messages,
        Query: Query,
        Result: List<TrafficFineResult>?,
        WarningMessage: String,
        Prerequisites: Prerequisites?
    ) : BaseResultModel<List<TrafficFineResult>>(
        Query,
        Result,
        WarningMessage,
        Prerequisites,
        Messages
    )

    data class TrafficFineResult(
        val Amount: Long,
        val DateTime: String?,
        val ExtraInfo: ExtraInfo,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String,
        var selected: Boolean = true
    )

    data class ExtraInfo(
        val Address: String,
        val Delivery: String,
        val DeliveryIcon: String,
        val HasImage: Boolean,
        val OfficerIdentificationCode: String,
        val SerialNumber: String,
        val Type: String,
        val TypeCode: String
    )
}