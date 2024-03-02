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

}