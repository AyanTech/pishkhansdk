package ir.ayantech.pishkhansdk

import android.annotation.SuppressLint
import android.content.Context
import ir.ayantech.whygoogle.helper.PreferencesManager

@SuppressLint("StaticFieldLeak")
object Constant {
    lateinit var context: Context

    var Token: String
        get() = PreferencesManager.getInstance(context).read("Token")
        set(value) = PreferencesManager.getInstance(context).save("Token", value)
}