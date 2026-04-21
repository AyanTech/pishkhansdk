package ir.ayantech.pishkhansdk.ui.components

import android.content.Context
import android.text.InputType
import androidx.annotation.UiContext
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.ComponentInputAmountCounterBinding
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentInputBinding
import ir.ayantech.pishkhansdk.helper.extensions.AfterTextChangedCallback
import ir.ayantech.pishkhansdk.helper.extensions.changeEnable
import ir.ayantech.pishkhansdk.helper.extensions.formatAmount
import ir.ayantech.pishkhansdk.helper.extensions.getColor
import ir.ayantech.pishkhansdk.helper.extensions.getCurrencyValue
import ir.ayantech.pishkhansdk.helper.extensions.placeCursorToEnd
import ir.ayantech.pishkhansdk.helper.extensions.reformatAmountToNumber
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.formatAmount
import ir.ayantech.whygoogle.helper.makeGone
import ir.ayantech.whygoogle.helper.makeVisible
import java.util.Locale
import kotlin.compareTo

fun ComponentInputAmountCounterBinding.initInputAmountCounterComponent(
    increaseAndDecreaseValue: Int = 1,
    unit: String? = null,
    hint: String? = null,
    defaultValue: Int? = null,
    minimumValue: Int = 1,
    amountValue: String? = null,
    context: Context,
    onInputChanges: (intValue: Long?) -> Unit
) {
    amountInputComponent.init(
        hint = hint ?: "مبلغ (ریال)",
        inputType = InputType.TYPE_CLASS_NUMBER,
        maxLength = 14,
        context = context
    )

    unitTv.text = unit ?: "ریال"

    setOnTextChangesListener {
        val intValue = getCountAsInt()
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

//    amountValue?.let {
//        amountTextTv.text = it
//        amountTextTv.makeVisible()
//    }

    increaseBtnComponent.init(
        icon = R.drawable.pishkhansdk_ic_add,
//        tint = getColor(R.color.color_primary)
    ) {
        increase(increaseAndDecreaseValue)
    }
    decreaseBtnComponent.init(
        icon = R.drawable.pishkhansdk_ic_remove,
    ) {
        decrease(increaseAndDecreaseValue, minimumValue)
    }
}

private fun ComponentInputAmountCounterBinding.setOnTextChangesListener(
    enableDecimalFlag: Boolean = false,
    onTextChanges: AfterTextChangedCallback?,
) {
    amountInputComponent.setAfterTextChangesListener { newText ->

        unitTv.changeVisibility(show = newText.isNotEmpty())

        if (newText.isEmpty())
            return@setAfterTextChangesListener

        val currentAmount = newText.getCurrencyValue()
        if (newText.length == 1 && currentAmount == 0L)
            return@setAfterTextChangesListener

        if (!enableDecimalFlag) {
//            if (currentAmount != null && currentAmount > 9L) {
//                amountTextTv.makeVisible()
//                amountTextTv.text =
//                    "${PersianDigits.spellToPersian(currentAmount.toString().dropLast(1))} تومان"
//            } else {
//                amountTextTv.makeGone()
//            }
            val formattedCurrentAmount = currentAmount?.formatAmount("")
            if (formattedCurrentAmount != newText) {
                formattedCurrentAmount?.let {
                    amountInputComponent.setText(formattedCurrentAmount)
                    amountInputComponent.placeCursorToEnd()
                }
            }
        }

        onTextChanges?.invoke(newText)
    }
}

fun ComponentInputAmountCounterBinding.increase(value: Int = 1) {
    val count = getCountAsInt().plus(value)
    amountInputComponent.setText(count.formatAmount(""))
}

fun ComponentInputAmountCounterBinding.decrease(value: Int = 1, minimumValue: Int = 1) {
    val count = maxOf(getCountAsInt().toInt().minus(value), minimumValue)
    amountInputComponent.setText(count.formatAmount(""))
}

fun ComponentInputAmountCounterBinding.increase(value: Double = 1.0) {
    val count = getCountAsDouble().plus(value)
    amountInputComponent.setText(String.format(Locale.getDefault(), "%.2f", count))
}

fun ComponentInputAmountCounterBinding.decrease(value: Double = 1.0, minimumValue: Double = 1.0) {
    val count = maxOf(getCountAsDouble().minus(value), minimumValue)
    amountInputComponent.setText(String.format(Locale.getDefault(), "%.2f", count))
}

fun ComponentInputAmountCounterBinding.getCountAsInt() = amountInputComponent.getText().reformatAmountToNumber().toLongOrNull() ?: 0

fun ComponentInputAmountCounterBinding.getCountAsDouble() = amountInputComponent.getText().toDoubleOrNull() ?: 0.0

fun ComponentInputAmountCounterBinding.clearText() {
    amountInputComponent.setText("")
}
fun ComponentInputAmountCounterBinding.setText(text: String?) {
    amountInputComponent.setText(text)


}
fun ComponentInputAmountCounterBinding.getAmount() =
amountInputComponent.getText().reformatAmountToNumber().toLongOrNull() ?: 0