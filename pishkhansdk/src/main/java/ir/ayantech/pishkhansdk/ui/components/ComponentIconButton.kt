package ir.ayantech.pishkhansdk.ui.components

import android.view.View.OnClickListener
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentIconButtonBinding

fun PishkhansdkComponentIconButtonBinding.init(icon: Int, onClickListener: OnClickListener? = null) {
    iconIv.setImageResource(icon)
    iconIv.setOnClickListener(onClickListener)
}