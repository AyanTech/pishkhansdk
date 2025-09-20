package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.WALLET_PAYMENT)
class WalletPayment {
    data class Input(
        val PaymentKey: String
    )
}