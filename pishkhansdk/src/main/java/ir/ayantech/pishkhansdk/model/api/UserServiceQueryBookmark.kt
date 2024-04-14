package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserServiceQueryBookmark)
class UserServiceQueryBookmark {
    data class Input(
        val Favorite: Boolean,
        val QueryUniqueID: String
    )
}