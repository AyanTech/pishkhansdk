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

@AyanAPI(endPoint = EndPoints.WaterBills)
class ServiceBills {
    class Input(
        OTPCode: String? = null,
        val Identifier: String,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: Result?,
        WarningMessage: String
    ) : BaseResultModel<Result>(
        Messages = Messages,
        Query = Query,
        Result = Result,
        WarningMessage = WarningMessage,
        Prerequisites = Prerequisites
    )

    data class Result(
        val Amount: Long,
        val DateTime: String,
        val ExtraInfo: ResultExtraInfo?,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String
    )

    data class ResultExtraInfo(
        val AdditionalInformation: String?,
        val Address: String,
        val DateTimeVisitCurrent: String?,
        val DateTimeVisitPrevious: String?,
        val FullName: String
    )
}
