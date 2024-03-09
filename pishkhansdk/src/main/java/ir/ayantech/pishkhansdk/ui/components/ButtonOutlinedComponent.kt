package ir.ayantech.pishkhansdk.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.ComponentButtonOutlinedBinding
import ir.ayantech.pishkhansdk.helper.getDimension
import ir.ayantech.pishkhansdk.helper.getDimensionInt
import ir.ayantech.whygoogle.helper.changeVisibility


fun ComponentButtonOutlinedBinding.init(
    context: Context,
    btnText: String,
    tint: Int? = null,
    btnOnClick: View.OnClickListener? = null
) {
    outlinedButton.text = btnText
    outlinedButton.setOnClickListener(btnOnClick)
    tint?.let {
        outlinedButton.setTextColor(ColorStateList.valueOf(tint))

        val gradientDrawable = GradientDrawable()
        gradientDrawable.setStroke(context.getDimensionInt(R.dimen.margin_1), tint)
        gradientDrawable.cornerRadius = context.getDimension(R.dimen.margin_12)
        gradientDrawable.setColor(getColor(context, R.color.transparent))

        val gradientDrawableDefault = GradientDrawable()
        gradientDrawableDefault.setStroke(context.getDimensionInt(R.dimen.margin_1), tint)
        gradientDrawableDefault.setColor(getColor(context, R.color.transparent))
        gradientDrawableDefault.cornerRadius = context.getDimension(R.dimen.margin_12)
        gradientDrawableDefault.setColor(getColor(context, R.color.transparent))

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_activated),
            gradientDrawableDefault
        )

        stateListDrawable.addState(StateSet.WILD_CARD, gradientDrawable)
        outlinedButton.background = stateListDrawable
        outlinedButton.isSelected = true
    }
}

fun ComponentButtonOutlinedBinding.changeEnable(
    enable: Boolean
) {
    outlinedButton.isEnabled = enable
}

fun ComponentButtonOutlinedBinding.changeVisibility(isVisible: Boolean) {
    outlinedButton.changeVisibility(show = isVisible)
}

fun ComponentButtonOutlinedBinding.setText(text: String) {
    outlinedButton.text = text
}