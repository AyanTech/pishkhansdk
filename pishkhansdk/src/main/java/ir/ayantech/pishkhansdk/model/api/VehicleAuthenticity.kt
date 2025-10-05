package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.VehicleAuthenticity)
class VehicleAuthenticity {

    @Deprecated(message = "Use version 3 of this API. New class: VehicleAuthenticityV3.Input()")
    class Input(
        val Identifier: String,
        val IdentifierType: String,
        val NationalCodeBuyer: String,
        val NationalCodeOwner: String,
        OTPCode: String? = "",
        val OTPReferenceNumber: String = "",
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    @Deprecated(message = "Use version 3 of this API. New class: VehicleAuthenticityV3.Output()")
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

    data class TechnicalDiagnosisStatus(
        val FuelSystem: String,
        val Status: String,
        val ValidityDate: String,
    )

    data class FactoryInformation(
        val WarrantyEndDate: String,
        val WarrantyStartDate: String,
        val WarrantyStatus: String,
    )

    data class Result(
        val Color: String,
        val TrafficFinesDebtStatus: TrafficFinesDebtStatus,
        val FreewayTollsDebtStatus: FreewayTollsDebtStatus,
        val TaxDebtStatus: TaxDebtStatus,
        val Fuel: String,
        val Insurance: Insurance,
        val Model: String,
        val OwnershipChanged: String,
        val Status: String,
        val Salable: Boolean,
        val Tip: String,
        val Type: String,
        val Usage: String,
        val TechnicalDiagnosisStatus: TechnicalDiagnosisStatus,
        val FactoryInformation: FactoryInformation,
    )

    data class Insurance(
        val EndDate: String,
        val StartDate: String,
        val Usage: List<String>
    )

    data class TrafficFinesDebtStatus(
        val Amount: Long,
        val Description: String,
    )

    data class FreewayTollsDebtStatus(
        val Amount: Long,
        val Description: String
    )

    data class TaxDebtStatus(
        val Amount: Long,
        val Description: String
    )

}