package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.CNPG_REMOVE_CARD)
class CnpgRemoveCard {
    data class Input(val PaymentKey: String, val CardID: String)
}