package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.VehiclePlateNumbers)
class VehiclePlateNumbers {
    class Input(
        val MobileNumber: String,
        val NationalCode: String,
        OTPCode: String?,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: List<VehiclePlateNumbersItem>?,
        WarningMessage: String
    ) : BaseResultModel<List<VehiclePlateNumbersItem>>(
        Query,
        Result,
        WarningMessage,
        Prerequisites,
        Messages
    )

    data class VehiclePlateNumbersItem(
        val NationalCode: String,
        val PlateNumber: String,
        val Revoked: Boolean,
        val RevokedDetail: RevokedDetail?,
        val SerialNumber: String
    )

    data class RevokedDetail(
        val DateTime: String,
        val Description: String
    )
}