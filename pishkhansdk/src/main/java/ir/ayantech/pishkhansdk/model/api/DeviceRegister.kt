package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.DeviceRegister)
class DeviceRegister {
    data class Input(
        val Application: String,
        val Origin: String,
        val Platform: String,
        val Version: String
    )

    data class Output(
        val Session: String
    )
}