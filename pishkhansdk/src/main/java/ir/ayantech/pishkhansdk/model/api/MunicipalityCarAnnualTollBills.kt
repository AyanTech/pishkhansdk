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

@AyanAPI(endPoint = EndPoints.MunicipalityCarAnnualTollBills)
class MunicipalityCarAnnualTollBills {
    class Input(
        OTPCode: String? = null,
        val EngineNumber: String,
        val NationalCode: String,
        val PlateNumber: String,
        PurchaseKey: String?,
        TaxGroup: String?,
        val VIN: String
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey, TaxGroup = TaxGroup)

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
        val Amount: Long,
        val DateTime: String,
        val ExtraInfo: ExtraInfo,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String
    )

    data class ExtraInfo(
        val Debt: Long,
        val Frazzle: Long,
        val FullName: String,
        val Penalty: Long,
        val VehicleDescription: String
    )
}