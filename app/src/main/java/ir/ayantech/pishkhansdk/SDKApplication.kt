package ir.ayantech.pishkhansdk

import android.app.Application
import android.content.Context
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


    var servicesPishkhan24Api: AyanApi? = null

    var corePishkhan24Api: AyanApi? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        val gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create()

        servicesPishkhan24Api = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson,

            )

        corePishkhan24Api = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken()  },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/core.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )


        PishkhanSDK.initialize(
            context = this,
            schema = "finesdetail",
            host = "ir.ayantech.finesdetail",
            corePishkhan24Api = corePishkhan24Api!!,
            servicesPishkhan24Api = servicesPishkhan24Api!!,

            )

    }
}