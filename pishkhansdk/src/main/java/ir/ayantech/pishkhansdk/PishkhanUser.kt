package ir.ayantech.pishkhansdk

import android.annotation.SuppressLint
import android.content.Context
import ir.ayantech.whygoogle.helper.PreferencesManager

@SuppressLint("StaticFieldLeak")
object PishkhanUser {
    lateinit var context: Context
    var token: String
        get() = PreferencesManager.getInstance(context).read("session")
        set(value) = PreferencesManager.getInstance(context).save("session", value)

    var schema: String
        get() = PreferencesManager.getInstance(context).read("schema")
        set(value) = PreferencesManager.getInstance(context).save("schema", value)

    var host: String
        get() = PreferencesManager.getInstance(context).read("host")
        set(value) = PreferencesManager.getInstance(context).save("host", value)

    var prefix: String
        get() = PreferencesManager.getInstance(context).read("prefix")
        set(value) = PreferencesManager.getInstance(context).save("prefix", value)

    var isUserSubscribed: Boolean
        get() = PreferencesManager.getInstance(context).read("isUserSubscribed")
        set(value) = PreferencesManager.getInstance(context).save("isUserSubscribed", value)

    var phoneNumber: String
        get() = PreferencesManager.getInstance(context).read("phoneNumber")
        set(value) = PreferencesManager.getInstance(context).save("phoneNumber", value)
}