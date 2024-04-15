package ir.ayantech.pishkhan24.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.VehicleThirdPartyInsurance)
class VehicleThirdPartyInsurance {
    class Input(
        OTPCode: String? = null,
        val InsuranceUniqueCode: String,
        val NationalCode: String,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: Result?,
        WarningMessage: String
    ) : BaseResultModel<Result>(
        Query = Query,
        Result = Result,
        WarningMessage = WarningMessage,
        Prerequisites = Prerequisites,
        Messages = Messages
    )

    data class Result(
        val ChassisNumber: String,
        val CompanyName: String,
        val DateTimeEnd: String,
        val DateTimeIssue: String,
        val DateTimeStart: String,
        val Driver: Driver,
        val EngineNumber: String,
        val Financial: Driver,
        val FullName: String,
        val InsuranceNumber: String,
        val Insurancetype: String,
        val Passenger: Driver,
        val PlateNumber: String,
        val VIN: String,
        val VehicleName: String
    )

    data class Driver(
        val DiscountPercent: String,
        val NumberOfYearsWithoutDamage: String,
        val Obligations: Long
    )
}