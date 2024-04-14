package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserSessionUpdateInfo)
class UserSessionUpdateInfo {
    data class Input(
        val Origin: String,
        val Version: String
    )
}