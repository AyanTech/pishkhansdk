package ir.ayantech.pishkhansdk.ui.components

import android.annotation.SuppressLint
import android.view.View.OnClickListener
import androidx.annotation.DrawableRes
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentBankCardNumberBinding
import ir.ayantech.pishkhansdk.helper.extensions.addChar
import ir.ayantech.pishkhansdk.helper.extensions.loadFromString
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull

@SuppressLint("ClickableViewAccessibility")
fun PishkhansdkComponentBankCardNumberBinding.initBankCardNumberComponent(
    hint: String,
    @DrawableRes endIconDrawable: Int? = R.drawable.pishkhansdk_ic_arrow_down,
    onClickListener: OnClickListener
) {
    root.setOnClickListener(onClickListener)
    hintTv.text = hint
    endIconDrawable?.let { iconIv.setImageResource(it) }
}

fun PishkhansdkComponentBankCardNumberBinding.setText(text: String, dashEnabled: Boolean = true) {
    hintTv.text = if (dashEnabled) text.addChar('-', 4) else text
}

fun PishkhansdkComponentBankCardNumberBinding.setStartIcon(@DrawableRes id: Int?) {
    id?.let { startIconIv.setImageResource(it) }
    startIconIv.changeVisibility(show = id.isNotNull())
}

fun PishkhansdkComponentBankCardNumberBinding.setStartIcon(url: String?) {
    url?.let { startIconIv.loadFromString(it) }
    startIconIv.changeVisibility(show = url.isNotNull())
}

fun PishkhansdkComponentBankCardNumberBinding.getText() = hintTv.text.toString().replace("-", "")

fun PishkhansdkComponentBankCardNumberBinding.isFilled() = hintTv.text.toString().replace("-", "").length == 16

fun PishkhansdkComponentBankCardNumberBinding.setError(error: String?) {
    hintTv.error = error
}