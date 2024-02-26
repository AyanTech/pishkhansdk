package ir.ayantech.pishkhansdk.ui.components

import android.text.InputType
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.google.android.material.textfield.TextInputLayout
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.ComponentTextFieldsBinding
import ir.ayantech.pishkhansdk.helper.extensions.placeCursorToEnd
import ir.ayantech.pishkhansdk.helper.extensions.setMaxLength
import ir.ayantech.pishkhansdk.helper.extensions.textChanges
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.StringCallBack
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull

fun ComponentTextFieldsBinding.init(
    hint: String,
    maxLength: Int? = null,
    isFocusable: Boolean = true,
    inputType: Int = InputType.TYPE_CLASS_NUMBER,
    hasRetryTv: Boolean = false,
    endIconMode: Int = TextInputLayout.END_ICON_CLEAR_TEXT,
    editTextStyle: Int = R.style.text_normal_primary_dark,
    iconButtonDrawable: Int? = null,
    text: String? = null,
    onIconButtonClicked: SimpleCallBack? = null,
    onTextChangesCallback: StringCallBack? = null,
    onEditTextClicked: SimpleCallBack? = null,
) {
    outlinedTextField.editText?.isFocusable = isFocusable
    outlinedTextField.editText?.letterSpacing =
        if (inputType == InputType.TYPE_CLASS_PHONE || inputType == InputType.TYPE_CLASS_NUMBER) 0.3f else 0f
    outlinedTextField.hint = hint
    outlinedTextField.editText?.setText(text)
    maxLength?.let { outlinedTextField.editText?.setMaxLength(it) }
    outlinedTextField.editText?.inputType = inputType
    retryTv.changeVisibility(hasRetryTv)
    outlinedTextField.endIconMode = endIconMode
    TextViewCompat.setTextAppearance(outlinedTextField.editText as TextView, editTextStyle)
    iconButtonLayout.root.changeVisibility(iconButtonDrawable.isNotNull())
    iconButtonDrawable?.let {
        iconButtonLayout.init(icon = iconButtonDrawable)
    }
    iconButtonLayout.root.setOnClickListener {
        onIconButtonClicked?.invoke()
    }
    outlinedTextField.editText?.textChanges {
        this.setError(null)
        onTextChangesCallback?.invoke(it)
    }
    outlinedTextField.editText?.setOnFocusChangeListener { _, isFocused ->
        if (!isFocused) {
            if (this.getText().isNotEmpty()) {
                this.setError(null)
            }
        } else {
            outlinedTextField.editText?.setText(outlinedTextField.editText?.text)
        }
    }
    outlinedTextField.editText?.setOnClickListener {
        onEditTextClicked?.invoke()
    }

}

fun ComponentTextFieldsBinding.setError(errorText: String?) {
    outlinedTextField.error = errorText
}

fun ComponentTextFieldsBinding.getText(): String {
    return outlinedTextField.editText?.text.toString()
}

fun ComponentTextFieldsBinding.setText(text: String) {
    outlinedTextField.editText?.setText(text)

}

fun ComponentTextFieldsBinding.placeCursorToEnd() {
    outlinedTextField.editText?.placeCursorToEnd()
}

fun ComponentTextFieldsBinding.setHint(hint: String) {
    outlinedTextField.hint = hint
}