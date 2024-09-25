package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.CarPlateNumberHistory)
class CarPlateNumberHistory {
    class Input(
        val MobileNumber: String,
        val NationalCode: String,
        OTPCode: String?,
        val PlateNumber: String,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: Result?,
        WarningMessage: String
    ) : BaseResultModel<Result>(Query, Result, WarningMessage, Prerequisites, Messages)

    data class Result(
        val PlateHistory: List<PlateHistory>,
        val PlateStatus : String,
        val TraceNumber : String
    )

    data class PlateHistory(
        val AttachDate: SimpleDate,
        val DetachDate: SimpleDate?,
        val VehicleBrand: String?,
        val VehicleModel: String?,
        val VehicleType: String?,
    )

    data class SimpleDate(
        val Gregorian : String,
        val Persian : String,
    )


}