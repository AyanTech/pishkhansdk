package ir.ayantech.pishkhansdk.model.constants

import android.annotation.SuppressLint
import android.content.Context
import ir.ayantech.whygoogle.helper.PreferencesManager

@SuppressLint("StaticFieldLeak")
object Constant {
    lateinit var context: Context
    const val DEEP_LINK_PREFIX = "pishkhan24:"
    const val callBackDataModel = "callBackDataModel"

    const val paid = "Paid"
    const val unpaid = "Unpaid"
    const val Settle = "Settle"

}