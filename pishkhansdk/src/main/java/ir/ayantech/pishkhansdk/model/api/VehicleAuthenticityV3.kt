package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import com.google.gson.annotations.SerializedName
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.VEHICLE_AUTHENTICITY_V3)
class VehicleAuthenticityV3 {

    class Input(
        val Identifier: String,
        val IdentifierType: String,
        val NationalCode: String,
        val MobileNumber: String,
        val BirthDate: String,
        OTPCode: String? = "",
        var OTPReferenceNumber: String = "",
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
        val FreewayTollDebtInfo: FreewayTollDebtInfo,
        val InsuranceDamageHistoryInfo: InsuranceDamageHistoryInfo,
        val IsTransferable: Boolean,
        val NumberingInfo: NumberingInfo,
        val ReceiptUrl: String,
        val Status: String,
        val TechnicalExaminationInfo: TechnicalExaminationInfo,
        val TrackingCode: String,
        val TrafficFineDebtInfo: TrafficFineDebtInfo,
        val TransferTaxDebtInfo: TransferTaxDebtInfo,
        val WarrantyInfo: WarrantyInfo,
    )

    enum class IdentifierType {
        Barcode,
        DocumentNumber,
    }

    data class FreewayTollDebtInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val TotalAmount: String,
    )

    data class Damages(
        val DamagesAreaDescription: String,
        val PaidAmount: String,
        val PaidDate: String,
        val TypeStatus: String,
    )

    data class InsuranceDamageHistoryInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val Damages: ArrayList<Damages>,
    )

    data class NumberingInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val Color: String,
        val FuelType: String,
        val MaskedPlateNumber: String,
        val Model: String,
        val OwnershipChangedNumber: String,
        val SubUsage: String,
        val System: String,
        val Tip: String,
        val TruncatedChassisNumber: String,
        val Usage: String,
    )

    data class TechnicalExaminationInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val ExpiredDateTime: String,
        val TestType: String,
        val ValidTestStatus: String,
    )

    data class TrafficFineDebtInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val TotalAmount: String,
    )

    data class TaxList(
        val Amount: String,
        val SuspendStatus: String,
        val Title: String,
    )

    data class TransferTaxDebtInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val TaxList: ArrayList<TaxList>,
    )

    data class WarrantyInfo(
        val Description: String,
        val InquiryStatus: Boolean,
        val EndDate: String,
        val Km: String,
        val LastDateService: String,
        val ReplacedBodyStatus: String,
        val ReplacedCarRoomStatus: String,
        val ReplacedGearBoxStatus: String,
        val ReplacedMotorStatus: String,
        val StartDate: String,
        val StatusName: String,
    )
}