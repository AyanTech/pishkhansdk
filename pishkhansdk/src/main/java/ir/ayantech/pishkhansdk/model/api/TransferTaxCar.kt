package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.TransferTaxCar)
class TransferTaxCar {
    class Input(
        OTPCode: String? = null,
        val DateOfBirth: String,
        val NationalCode: String,
        val PlateNumber: String,
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
        val InquiryID: String,
        val NasimID: String,
        val Owner: String,
        val PaidAmount: Long,
        val PayableAmount: Long,
        val PaymentUrl: String,
        val Tax: Long,
        val TermsAndConditions: String,
        val VehicleModel: String,
        val VehicleType: String,
        val ReceiptCertificate: String,

        )
}