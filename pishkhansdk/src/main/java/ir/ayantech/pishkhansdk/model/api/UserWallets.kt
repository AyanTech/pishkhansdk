package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.USER_WALLETS)
class UserWallets {
    class Output : ArrayList<Wallet>()

    data class Wallet(
        val ChargeSettings: ChargeSettings,
        val Help: String? = null,
        val Icon: String?,
        val TermsAndConditions: String? = null,
        val Type: Type,
        var Balance: Long? = null,
        var Credit: Long? = null,
        var ApiFailed: Boolean,
    )

    data class ChargeSettings(
        val Interval: Long,
        val Maximum: Long,
        val Minimum: Long,
        val SuggestedAmounts: List<Long>
    )
}