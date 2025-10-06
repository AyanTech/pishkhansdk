package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.CAR_ANNUAL_TAX_GET_SETTLEMENT_CERTIFICATE)
class CarAnnualTaxGetSettlementCertificate {

    class Input(
        OTPCode: String? = "",
        PurchaseKey: String?,
        val BillUniqueID: String
    ): BaseInputModel(
        OTPCode =OTPCode,
        PurchaseKey = PurchaseKey
    )

    class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: Result?,
        WarningMessage: String
    ): BaseResultModel<Result>(
        Query = Query,
        Result = Result,
        WarningMessage = WarningMessage,
        Prerequisites = Prerequisites,
        Messages = Messages
    )

    data class Result(
        val CertificateUrl: String?
    )
}