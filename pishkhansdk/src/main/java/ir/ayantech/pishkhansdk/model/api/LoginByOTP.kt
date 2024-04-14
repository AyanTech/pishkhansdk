package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.OTP
import ir.ayantech.pishkhansdk.model.constants.EndPoints


@AyanAPI(endPoint = EndPoints.LoginByOTP)
class LoginByOTP {
    data class Input(
        val OTPCode: String?,
        val Username: String
    )

    data class Output(
        val OTP: OTP
    )
}