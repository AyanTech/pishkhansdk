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

@AyanAPI(endPoint = EndPoints.FreewayTollBills)
class FreewayTollBills {
    class Input(
        OTPCode: String? = null,
        val PlateNumber: String,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: List<Result>?,
        WarningMessage: String
    ) : BaseResultModel<List<Result>>(
        Query = Query,
        Result = Result,
        WarningMessage = WarningMessage,
        Prerequisites = Prerequisites,
        Messages = Messages
    )

    data class Result(
        val Amount: Long,
        val Bills : List<Bills>,
        val Count : Int,
        val Date : String,
        val DayName : String,
        val Title : String
     )

    data class ExtraInfo(
        val DateTimeTraverse: String,
        val Freeway: String
    )
    data class Bills(
        val Amount: Long,
        val DateTime: String,
        val Payment: Payment,
        val type: Type,
        val UniqueID: String,
        val ExtraInfo: ExtraInfo,

        )
}