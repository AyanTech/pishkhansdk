package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.DrivingLicenseStatus)
class DrivingLicenseStatus {
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
        Result: List<Result>?,
        WarningMessage: String
    ) : BaseResultModel<List<Result>>(
        Query,
        Result,
        WarningMessage,
        Prerequisites,
        Messages
    )

    data class Result(
        val Barcode: String,
        val DateTime: String?,
        val FirstName: String,
        val LastName: String,
        val NationalCode: String,
        val Number: String,
        val Status: String,
        val Type: String,
        val ValidityPeriodInYears: String
    )
}