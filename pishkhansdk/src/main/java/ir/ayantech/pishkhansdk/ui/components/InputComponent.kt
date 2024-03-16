package ir.ayantech.pishkhansdk.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.View
import android.view.View.OnTouchListener
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.updatePadding
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.ComponentInputBinding
import ir.ayantech.pishkhansdk.helper.extensions.AfterTextChangedCallback
import ir.ayantech.pishkhansdk.helper.extensions.placeCursorToEnd
import ir.ayantech.pishkhansdk.helper.extensions.setMaxLength
import ir.ayantech.pishkhansdk.helper.extensions.textChanges
import ir.ayantech.pishkhansdk.helper.extensions.getDimensionInt
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility

@SuppressLint("ClickableViewAccessibility")
fun ComponentInputBinding.init(
    context: Context,
    hint: String? = null,
    text: String? = null,
    @ColorRes backgroundTint: Int? = null,
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    helperText: String? = null,
    maxLength: Int? = null,
    @DrawableRes startDrawable: Int? = null,
    @DrawableRes endDrawable: Int? = null,
    @ColorRes startIconColor: Int? = null,
    @ColorRes endIconColor: Int? = null,
    onStartIconClicked: SimpleCallBack? = null,
    onEndIconClicked: SimpleCallBack? = null,
    textAlignment: Int? = null,
    onStartIconTouchListener: OnTouchListener? = null,
    onEndIconTouchListener: OnTouchListener? = null,
    @DimenRes startIconPadding: Int? = null,
    @DimenRes endIconPadding: Int? = null,
) {
    backgroundTint?.let { textInputLayout.boxBackgroundColor = getColor(context, backgroundTint) }
    textInputEditText.apply {
        this.hint = hint
        setText(text)

        this.inputType = inputType

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(R.style.GlobalTextInputEditText)
        }

        maxLength?.let { setMaxLength(it) }

        textAlignment?.let {
            setTextAlignment(textAlignment)
            if (textAlignment == View.TEXT_ALIGNMENT_VIEW_START)
                textInputEditText.updatePadding(0, 0, context.getDimensionInt(R.dimen.margin_40), 0)

            if (textAlignment == View.TEXT_ALIGNMENT_VIEW_END)
                textInputEditText.updatePadding(0, 0, context.getDimensionInt(R.dimen.margin_40), 0)
        }
    }

    textInputLayout.apply {
        this.helperText = helperText
    }
}


fun ComponentInputBinding.getText() = textInputEditText.text?.toString() ?: ""

fun ComponentInputBinding.setText(text: String?) {
    textInputEditText.setText(text)
}

fun ComponentInputBinding.placeCursorToEnd() {
    textInputEditText.placeCursorToEnd()
}

fun ComponentInputBinding.changeVisibility(show: Boolean) {
    root.changeVisibility(show = show)
}

fun ComponentInputBinding.setAfterTextChangesListener(afterTextChangedCallback: AfterTextChangedCallback) {
    textInputEditText.textChanges(afterTextChangedCallback = afterTextChangedCallback)
}

fun ComponentInputBinding.setError(text: String?) {
    textInputLayout.error = text
}
