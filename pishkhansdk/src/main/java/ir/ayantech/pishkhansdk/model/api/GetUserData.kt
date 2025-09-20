package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.GET_USER_DATA)
class GetUserData {
    data class Input(
        val Key: String?
    )

    data class Output(
        val Value: String?
    )
}