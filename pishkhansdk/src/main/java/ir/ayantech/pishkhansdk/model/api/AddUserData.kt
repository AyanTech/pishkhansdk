package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.ADD_USER_DATA)
class AddUserData {
    data class Input(
        val Key: String?,
        val Value: String
    )

    data class Output(
        val DataID: String
    )
}