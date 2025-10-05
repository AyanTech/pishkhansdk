package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.FREEWAY_TOLL_BILLS_DETAILED)
class FreewayTollBillsDetailed {

    class Input(
        OTPCode: String? = null,
        val PlateNumber: String,
        val MobileNumber: String,
        val NationalCode: String,
        PurchaseKey: String?
    ) : BaseInputModel(
        OTPCode = OTPCode, PurchaseKey = PurchaseKey
    )

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
        val Amount: Int,
        val DateTime: String,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String,
        val ExtraInfo: ExtraInfo,
    )

    data class ExtraInfo(
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

    data class PaymentExtraInfo(
        val DateTimeIssue: String,
        val DateTimeTraverse: String,
        val Freeway: String,
    )
}