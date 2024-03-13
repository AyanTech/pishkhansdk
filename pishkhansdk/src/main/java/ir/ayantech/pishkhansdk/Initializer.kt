package ir.ayantech.pishkhansdk

import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.ayannetworking.api.SimpleCallback
import ir.ayantech.networking.callUserSessionUpdateInfo
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.model.api.DeviceRegister
import ir.ayantech.pishkhansdk.model.api.UserSessionUpdateInfo
import ir.ayantech.pishkhansdk.model.constants.EndPoints

object Initializer {

    fun deviceRegister(
        application: String,
        origin: String,
        platform: String,
        version: String,
        corePishkhan24Api: AyanApi,
        successCallback: SimpleCallback?
    ) {
        corePishkhan24Api.ayanCall<DeviceRegister.Output>(
            AyanCallStatus {
                success {
                    it.response?.Parameters?.Session?.let {
                        PishkhanUser.token = it
                        updateUserSessions(
                            corePishkhan24Api = PishkhanSDK.coreApi,
                            origin = origin,
                            version = version,
                        )
                        successCallback?.invoke()
                    }
                }
                failure { }
            },
            EndPoints.DeviceRegister,
            DeviceRegister.Input(
                Application = application,
                Origin = origin,
                Platform = platform,
                Version = version
            ),
            hasIdentity = false
        )
    }

    fun updateUserSessions(
        corePishkhan24Api: AyanApi,
        origin: String,
        version: String
    ) {
        corePishkhan24Api.callUserSessionUpdateInfo(
            input = UserSessionUpdateInfo.Input(
                Origin = origin, Version = version
            )
        ) {

        }
    }
}