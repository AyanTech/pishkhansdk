package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.USER_PUBLIC_KEY)
class UserPublicKey {
    data class Output(val PublicKey: String)
}