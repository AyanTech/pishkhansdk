package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.USER_WALLET_CHARGE)
class UserWalletCharge {
    data class Input(
        val Amount: Long,
        val Callback: String,
        val Gateway: String,
        val Wallet: String
    )

    data class Output(
        val RedirectLink: String,
        val PaymentKey: String
    )
}