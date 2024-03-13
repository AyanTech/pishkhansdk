package ir.ayantech.pishkhansdk

import android.app.Application
import android.content.Context
import com.alirezabdn.whyfinal.BuildConfig
import com.google.gson.GsonBuilder
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.ayanModel.LogLevel
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import java.lang.reflect.Modifier


class SDKApplication : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    var coreAyanApi: AyanApi? = null
    var servicesAyanApi: AyanApi? = null


    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        val gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create()

        servicesAyanApi = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )


        coreAyanApi = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/core.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )


        PishkhanSDK.initialize(
            context = this,
            schema = "bankiban",
            host = "ir.ayantech.bankiban",
            corePishkhan24Api = coreAyanApi!!,
            servicesPishkhan24Api = servicesAyanApi!!,
        )

    }
}