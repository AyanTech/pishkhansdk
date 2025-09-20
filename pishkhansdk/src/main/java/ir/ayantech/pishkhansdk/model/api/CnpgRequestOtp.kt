package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.CNPG_REQUEST_OTP)
class CnpgRequestOtp {
    data class Input(val PaymentKey: String, val EncryptData: String)
}