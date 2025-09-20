package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.pishkhansdk.model.constants.EndPoints


@AyanAPI(endPoint = EndPoints.USER_WALLET_CHARGE_CHANNELS)
class UserWalletChargeChannels {
    data class Input(
        val Wallet: String
    )

    data class Output(
        val PaymentChannels: List<PaymentChannel>,
    )
}
