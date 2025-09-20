package ir.ayantech.pishkhansdk.ui.components

import android.content.Context
import android.text.InputType
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentBankCardDateBinding
import ir.ayantech.pishkhansdk.helper.extensions.changeEnable

fun PishkhansdkComponentBankCardDateBinding.initBankCardDateComponent(
    context: Context
) {
    monthInputComponent.init(
        context = context,
        hint = context.getString(R.string.month),
        maxLength = 2,
        inputType = InputType.TYPE_CLASS_NUMBER,
    )

    yearInputComponent.init(
        context = context,
        hint = context.getString(R.string.year),
        maxLength = 2,
        inputType = InputType.TYPE_CLASS_NUMBER,
    )
}

fun PishkhansdkComponentBankCardDateBinding.setDefaultDate() {
    setDate(year = "**", month = "**")
    changeEnable(isEnable = false)
    yearInputComponent.textInputEditText.changeEnable(false, disableAlpha = 0.7f)
    monthInputComponent.textInputEditText.changeEnable(false, disableAlpha = 0.7f)
}

fun PishkhansdkComponentBankCardDateBinding.clearDefaultDate() {
    setDate(year = "", month = "")
    changeEnable(isEnable = true)
    yearInputComponent.textInputEditText.changeEnable(true)
    monthInputComponent.textInputEditText.changeEnable(true)
}

fun PishkhansdkComponentBankCardDateBinding.setDate(year: String, month: String) {
    yearInputComponent.setText(year)
    monthInputComponent.setText(month)
}

fun PishkhansdkComponentBankCardDateBinding.getYearAndMonthText() = "${yearInputComponent.getText()}${monthInputComponent.getText()}"