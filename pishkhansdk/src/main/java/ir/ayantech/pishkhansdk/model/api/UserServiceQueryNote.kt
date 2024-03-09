package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserServiceQueryNote)
class UserServiceQueryNote {
    data class Input(
        val Note: String,
        val QueryUniqueID: String
    )
}