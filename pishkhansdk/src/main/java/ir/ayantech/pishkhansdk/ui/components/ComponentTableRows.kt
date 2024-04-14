package ir.ayantech.pishkhansdk.ui.components

import androidx.core.widget.TextViewCompat
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentTableRowsBinding
import ir.ayantech.pishkhansdk.helper.extensions.simpleUnify
import ir.ayantech.pishkhansdk.helper.extensions.textColor
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility

fun PishkhansdkComponentTableRowsBinding.init(
    key: String,
    value: String? = null,
    keyTvResId: Int,
    valueTvResId: Int,
    highlight: Boolean,
    startHighlightFromZero: Boolean,
    position: Int,
    highlightColor: Int,
    valueTextColor: Int = 0,
    buttonText: String? = null,
    buttonIcon: Int? = null,
    onActionButtonClicked: SimpleCallBack? = null
) {
    keyTv.text = key.simpleUnify()
    valueTv.text = value?.simpleUnify()
    valueTv.changeVisibility(!value.isNullOrEmpty())


    imageBtn.changeVisibility(!buttonText.isNullOrEmpty())
    actionTv.text = buttonText
    if (buttonIcon != null) {
        actionIv.setImageResource(buttonIcon)
    }

    imageBtn.setOnClickListener {
        onActionButtonClicked?.invoke()
    }

    TextViewCompat.setTextAppearance(keyTv, keyTvResId)
    TextViewCompat.setTextAppearance(valueTv, valueTvResId)


    if (highlight) {
        root.setBackgroundResource(
            if ((position + if (startHighlightFromZero) 1 else 0) % 2 == 0) R.color.white_table
            else highlightColor
        )
    }

    if (valueTextColor != 0) {
        valueTv.textColor(valueTextColor)
    }
}