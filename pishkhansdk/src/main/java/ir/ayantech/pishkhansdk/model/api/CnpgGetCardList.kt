package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.CNPG_GET_CARD_LIST)
class CnpgGetCardList {
    data class Input(val PaymentKey: String)
    data class Output(val CardList: List<BankCard>)
    data class BankCard(
        val BankID: Long,
        val CardID: String,
        val MaskedPan: String,
        var Icon: String? = null,
        var BankName: String? = null
    )
}