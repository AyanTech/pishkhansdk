package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.GovernmentSubventionHistory)
class SubventionHistory {
    class Input(
        val MobileNumber: String,
        OTPCode: String? = null,
        PurchaseKey: String?,
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
        val AccountNumber: String,
        val BankName: String,
        val FullName: String,
        val IbanNumber: String,
        val Payments: List<Payment>?,
        val Profile: Profile
    )

    data class Payment(
        val AccountNumber: String,
        val BankName: String,
        val Cycle: String,
        val Deciles: String,
        val Deductions: Long,
        val Deposit: Long,
        val Detail: List<Detail>,
        val TotalAmount: Long
    )

    data class Detail(
        val AdministratorNationalCode: String,
        val FatherName: String,
        val FirstName: String,
        val LastName: String,
        val NationalCode: String
    )

    data class Profile(
        val Address: String,
        val DateOfBirth: String,
        val Deciles: String,
        val Family: String,
        val FatherName: String,
        val Gender: String,
        val Name: String,
        val NationalCode: String,
        val SubsidyApply: Boolean
    )

}

