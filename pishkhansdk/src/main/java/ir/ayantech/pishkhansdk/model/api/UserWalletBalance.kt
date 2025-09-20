package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.USER_WALLET_BALANCE)
class UserWalletBalance {
    data class Input(
        val Wallet: String,
        val CreditBalance: Boolean? = false,
    )

    data class Output(
        val Cash: Long,
        val Credit: Long
    )
}