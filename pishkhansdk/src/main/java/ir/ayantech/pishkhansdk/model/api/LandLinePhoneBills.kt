package ir.ayantech.pishkhansdk.model.api

import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.Payment
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.LandLinePhoneBills)
class LandLinePhoneBills {
    class Input(
        OTPCode: String? = null,
        val LineNumber: String,
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
        val DateTime: String?,
        val ExtraInfo: LandLineExtraInfo,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String,
        var isSelected: Boolean = false,
        var hasDetails: Boolean = false
    )

    data class LandLineExtraInfo(
        val Cycle: String?,
        val Final: Boolean,
        val AdditionalInformation: String?
    )
}