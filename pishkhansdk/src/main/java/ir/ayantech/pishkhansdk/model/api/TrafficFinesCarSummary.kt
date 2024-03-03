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

@AyanAPI(endPoint = EndPoints.TrafficFinesCarSummary)
class TrafficFinesCarSummary {
    class Input(
        OTPCode: String? = null,
        val PlateNumber: String,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: TrafficFinesCarSummaryResult?,
        WarningMessage: String
    ) : BaseResultModel<TrafficFinesCarSummaryResult>(
        Query,
        Result,
        WarningMessage,
        Prerequisites,
        Messages
    )

    data class TrafficFinesCarSummaryResult(
        val Amount: Long,
        val DateTime: String,
        val ExtraInfo: ExtraInfo?,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String
    )

    data class ExtraInfo(
        val ComplaintCode: String?,
        val ComplaintStatus: String?,
        val PlateNumber: String?
    )
}