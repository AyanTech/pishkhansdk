package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.CAR_ANNUAL_TAX_BILLS)
class CarAnnualTaxBills {

    class Input(
        OTPCode: String? = "",
        PurchaseKey: String?,
        TaxGroup: String?,
        val IsLegal: Boolean = false,
        val NationalCode: String,
        val PlateNumber: String,
        val VIN: String,
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
        val Amount: Int,
        val DateTime: String,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String,
        val ExtraInfo: ExtraInfo,
        val TaxCalculations: ArrayList<TaxCalculations>,
    )

    data class PaymentExtraInfo(
        val DateTime: String,
        val SettlementCertificateUrl: String,
        val TraceNumber: String,
        val TransactionUniqueID: String,
    )

    data class Payment(
        val DeadLine: String,
        val ExtraInfo: PaymentExtraInfo,
        val Status: Type,
    )

    data class ExtraInfo(
        val FullName: String,
        val HasSettlementCertificate: Boolean,
        val TaxCalculations: String,
        val VehicleFuelType: String,
        val VehicleModel: String,
        val VehicleSystem: String,
        val VehicleUsage: String,
    )

    data class TaxCalculations(
        val Credit: Int,
        val Debit: Int,
        val Description: String,
        val Penalty: Int,
        val Remain: Int,
        val Tax: Int,
        val Total: Int,
        val TotalFrazzle: Int,
    )
}