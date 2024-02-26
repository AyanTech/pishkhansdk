package ir.ayantech.pishkhansdk

import android.util.Log
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.pishkhansdk.model.api.DeviceRegister
import ir.ayantech.pishkhansdk.model.constants.EndPoints

object Initializer {

    fun deviceRegister(
        Application: String,
        Origin: String,
        Platform: String,
        Version: String,
        corePishkhan24Api: AyanApi
    ) {
        corePishkhan24Api.ayanCall<DeviceRegister.Output>(
            AyanCallStatus {
                success {
                    it.response?.Parameters?.Session?.let {
                        Log.d("hfssdg",it)
                        PishkhanUser.token = it
                    }
                }
                failure { }
            },
            EndPoints.DeviceRegister,
            DeviceRegister.Input(
                Application = Application,
                Origin = Origin,
                Platform = Platform,
                Version = Version
            ),
            hasIdentity = false
        )
    }
}