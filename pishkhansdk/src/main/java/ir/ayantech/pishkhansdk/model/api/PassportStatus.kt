package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.PassportStatus)
class PassportStatus {
    class Input(
        val MobileNumber: String,
        val NationalCode: String,
        OTPCode: String?,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: Result?,
        WarningMessage: String
    ) : BaseResultModel<Result>(Query, Result, WarningMessage, Prerequisites, Messages)

    data class Result(
        val ExpirationDateTime: String?,
        val IssueDateTime: String?,
        val Number: String,
        val PostalBarcode: String,
        val RequestDateTime: String?,
        val RequestStatus: String,
        val Status: String
    )
}