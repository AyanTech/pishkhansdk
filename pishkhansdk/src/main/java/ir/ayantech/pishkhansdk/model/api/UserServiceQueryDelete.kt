package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserServiceQueryDelete)
class UserServiceQueryDelete {
    data class Input(
        val QueryUniqueID: String
    )
}