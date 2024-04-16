package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.Payment
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.BillsInfo)
class BillsInfo {
    data class Input(
        val BillID: String,
        val PaymentID: String
    )

    data class Output(
        val Amount: Long,
        val DateTime: String,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String
    )
}