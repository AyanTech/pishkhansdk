package ir.ayantech.pishkhansdk.helper.extensions

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

typealias OnTextChangedCallback = (() -> Unit)
typealias AfterTextChangedCallback = (String) -> Unit

fun EditText.setMaxLength(length: Int) {
    this.filters = arrayOf<InputFilter>(LengthFilter(length))
}

fun EditText.placeCursorToEnd() {
    this.setSelection(this.text.length)
}

fun EditText.textChanges(
    onTextChangedCallback: OnTextChangedCallback? = null,
    afterTextChangedCallback: AfterTextChangedCallback
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            afterTextChangedCallback(p0?.toString() ?: "")
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChangedCallback?.invoke()
        }
    })
}

fun TextInputEditText.setMaxLength(maxLength: Int) {
    filters = arrayOf(LengthFilter(maxLength))
}

fun TextInputEditText.placeCursorToEnd() {
    this.text?.let {
        this.setSelection(it.length)
    }
}