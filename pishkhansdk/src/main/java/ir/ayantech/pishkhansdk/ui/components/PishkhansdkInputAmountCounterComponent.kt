package ir.ayantech.pishkhansdk.ui.components

import android.content.Context
import android.text.InputType
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentInputAmountCounterBinding
import ir.ayantech.pishkhansdk.helper.extensions.AfterTextChangedCallback
import ir.ayantech.pishkhansdk.helper.extensions.changeEnable
import ir.ayantech.pishkhansdk.helper.extensions.formatAmount
import ir.ayantech.pishkhansdk.helper.extensions.reformatAmountToNumber
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.formatAmount
import ir.ayantech.whygoogle.helper.makeGone
import ir.ayantech.whygoogle.helper.makeVisible

fun PishkhansdkComponentInputAmountCounterBinding.initInputAmountCounterComponent(
    context: Context,
    increaseAndDecreaseValue: Int = 1,
    unit: String? = null,
    hint: String? = null,
    defaultValue: Int? = null,
    minimumValue: Int = 1,
    amountValue: String? = null,
    onInputChanges: (intValue: Long?) -> Unit
) {
    amountInputComponent.init(
        hint = hint ?: "مبلغ (ریال)",
        inputType = InputType.TYPE_CLASS_NUMBER,
        maxLength = 14,
        context = context,
        backgroundTint = R.color.transparent,
        boxStrokeColor = R.color.color_secondary,
        boxRadius = 8.0f
    )

    unitTv.text = unit ?: "ریال"

    setOnTextChangesListener {
        val intValue = getAmount()
        decreaseBtnComponent.changeEnable(isEnable = intValue > minimumValue)
        if (intValue < minimumValue) {
            onInputChanges(null)
            return@setOnTextChangesListener
        }
        onInputChanges(intValue)
    }

    defaultValue?.formatAmount("")?.let {
        amountInputComponent.setText(it)
        amountInputComponent.placeCursorToEnd()
    }

    amountValue?.let {
        amountTextTv.text = it
        amountTextTv.makeVisible()
    }

    increaseBtnComponent.init(
        icon = R.drawable.pishkhansdk_ic_add,
    ) {
        increase(increaseAndDecreaseValue)
    }
    decreaseBtnComponent.init(
        icon = R.drawable.pishkhansdk_ic_remove,
    ) {
        decrease(increaseAndDecreaseValue, minimumValue)
    }
}

private fun PishkhansdkComponentInputAmountCounterBinding.setOnTextChangesListener(
    onTextChanges: AfterTextChangedCallback?,
) {
    amountInputComponent.setAfterTextChangesListener { newText ->

        unitTv.changeVisibility(show = newText.isNotEmpty())

        if (newText.isEmpty())
            return@setAfterTextChangesListener

        onTextChanges?.invoke(newText)
    }
}

fun PishkhansdkComponentInputAmountCounterBinding.increase(value: Int = 1) {
    val count = getAmount().plus(value)
    amountInputComponent.setText(count.formatAmount(""))
}

fun PishkhansdkComponentInputAmountCounterBinding.decrease(value: Int = 1, minimumValue: Int = 1) {
    val count = maxOf(getAmount().toInt().minus(value), minimumValue)
    amountInputComponent.setText(count.formatAmount(""))
}

fun PishkhansdkComponentInputAmountCounterBinding.getAmount() = amountInputComponent.getText().reformatAmountToNumber().toLongOrNull() ?: 0

fun PishkhansdkComponentInputAmountCounterBinding.clearText() {
    amountInputComponent.setText("")
}

fun PishkhansdkComponentInputAmountCounterBinding.setText(text: String) {
    amountInputComponent.setText(text)
}

fun PishkhansdkComponentInputAmountCounterBinding.clearAmountValueText() {
    amountTextTv.text = null
    amountTextTv.makeGone()
}
fun PishkhansdkComponentInputAmountCounterBinding.setAmountValueText(text: String) {
    amountTextTv.text = text
    amountTextTv.makeVisible()
}