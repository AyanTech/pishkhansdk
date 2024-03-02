package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints


@AyanAPI(endPoint = EndPoints.JusticeSharesPortfolio)
class JusticeSharesPortfolio {
    class Input(
        val NationalCode: String,
        OTPCode: String?,
        PurchaseKey: String?
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    class Output(
        Query: Query,
        Result: JusticeResult?,
        WarningMessage: String,
        Prerequisites: Prerequisites?,
        Messages: Messages?,
    ) : BaseResultModel<JusticeResult>(
        Query,
        Result,
        WarningMessage,
        Prerequisites,
        Messages
    )

    data class JusticeResult(
        val PaymentReports: List<PaymentReport>,
        val Portfolio: List<Portfolio>,
        val PortfolioDescription: String,
        val PortfolioTotalAmount: Long,
        val PortfolioTotalCount: Long,
        val Profile: Profile
    )

    data class PaymentReport(
        val DepositAmount: Long,
        val DepositDate: String,
        val DepositStatus: String,
        val FirstName: String,
        val ID: Double,
        val Iban: String,
        val LastName: String,
        val NationalCode: String,
        val ProfitType: String,
        val Symbol: String
    )

    data class Portfolio(
        val AssetCount: Double,
        val AssetName: String,
        val AssetTotalValue: Double,
        val AssetTradableState: String,
        val AssetUnitTradeValue: Double,
        val AssetUnitValue: Double
    )

    data class Profile(
        val BankName: String,
        val City: String,
        val FatherName: String,
        val FullName: String,
        val MobileNumber: String,
        val NationalID: String,
        val Province: String,
        val Sheba: String
    )
}