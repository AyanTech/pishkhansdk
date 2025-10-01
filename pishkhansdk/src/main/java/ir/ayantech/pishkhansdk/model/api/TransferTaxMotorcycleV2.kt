package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import com.google.gson.annotations.SerializedName
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.TRANSFER_TAX_MOTORCYCLE_V2)
class TransferTaxMotorcycleV2 {
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
        Result: List<TransferTaxV2Output.Result>?,
        WarningMessage: String
    ) : BaseResultModel<List<TransferTaxV2Output.Result>>(
        Query = Query,
        Result = Result,
        WarningMessage = WarningMessage,
        Prerequisites = Prerequisites,
        Messages = Messages
    )

}