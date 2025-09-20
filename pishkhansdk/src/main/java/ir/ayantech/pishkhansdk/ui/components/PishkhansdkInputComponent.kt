package ir.ayantech.pishkhansdk.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.ColorSpace
import android.os.Build
import android.text.InputType
import android.view.View
import android.view.View.OnTouchListener
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.updatePadding
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentInputBinding
import ir.ayantech.pishkhansdk.helper.extensions.AfterTextChangedCallback
import ir.ayantech.pishkhansdk.helper.extensions.getDimensionInt
import ir.ayantech.pishkhansdk.helper.extensions.placeCursorToEnd
import ir.ayantech.pishkhansdk.helper.extensions.setMaxLength
import ir.ayantech.pishkhansdk.helper.extensions.setTint
import ir.ayantech.pishkhansdk.helper.extensions.textChanges
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull

@SuppressLint("ClickableViewAccessibility")
fun PishkhansdkComponentInputBinding.init(
    context: Context,
    hint: String? = null,
    text: String? = null,
    @ColorRes backgroundTint: Int? = null,
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    helperText: String? = null,
    maxLength: Int? = null,
    @ColorRes boxStrokeColor: Int? = null,
    boxRadius: Float? = null,
    @DrawableRes endDrawable: Int? = null,
    @ColorRes endIconColor: Int? = null,
    onEndIconClicked: SimpleCallBack? = null,
    textAlignment: Int? = null,
    onEndIconTouchListener: OnTouchListener? = null,
    @DimenRes endIconPadding: Int? = null,
) {
    backgroundTint?.let { textInputLayout.boxBackgroundColor = getColor(context, backgroundTint) }
    boxStrokeColor?.let { textInputLayout.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(context, it)) }
    boxRadius?.let {
        textInputLayout.setBoxCornerRadii(
            it,
            it,
            it,
            it
        )
    }
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

    endIconIv.changeVisibility(show = endDrawable.isNotNull())
    endIconPadding?.let {
        val padding = getDimensionInt(it)
        endIconIv.updatePadding(padding, padding, padding, padding)
    }
    endDrawable?.let { endIconIv.setImageResource(it) }
    endIconColor?.let { endIconIv.setTint(endIconColor) }
    onEndIconClicked?.let { endIconIv.setOnClickListener { it() } }
    onEndIconTouchListener?.let { endIconIv.setOnTouchListener(it) }
}


fun PishkhansdkComponentInputBinding.getText() = textInputEditText.text?.toString() ?: ""

fun PishkhansdkComponentInputBinding.setText(text: String?) {
    textInputEditText.setText(text)
}

fun PishkhansdkComponentInputBinding.placeCursorToEnd() {
    textInputEditText.placeCursorToEnd()
}

fun PishkhansdkComponentInputBinding.changeVisibility(show: Boolean) {
    root.changeVisibility(show = show)
}

fun PishkhansdkComponentInputBinding.setAfterTextChangesListener(afterTextChangedCallback: AfterTextChangedCallback) {
    textInputEditText.textChanges(afterTextChangedCallback = afterTextChangedCallback)
}

fun PishkhansdkComponentInputBinding.setError(text: String?) {
    textInputLayout.error = text
}

fun PishkhansdkComponentInputBinding.setInputType(inputType: Int) {
    textInputEditText.inputType = inputType
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        textInputEditText.setTextAppearance(R.style.GlobalTextInputEditText)
    }
}
