package ir.ayantech.pishkhansdk.ui.components

import android.os.SystemClock
import android.view.View
import ir.ayantech.pishkhansdk.databinding.ComponentButtonFilledBinding

private var mLastClickTime: Long = 0

fun ComponentButtonFilledBinding.init(
    btnText: String,
    isEnable: Boolean = true,
    btnOnClick: View.OnClickListener? = null
) {
    filledButton.text = btnText
    filledButton.setOnClickListener {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return@setOnClickListener
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        btnOnClick?.onClick(it)
    }
    changeEnable(enable = isEnable)
    filledButton.setHorizontallyScrolling(true)
    filledButton.isSelected = true
}

fun ComponentButtonFilledBinding.changeEnable(
    enable: Boolean
) {
    filledButton.isEnabled = enable
}

fun ComponentButtonFilledBinding.setText(text: String?) {
    filledButton.text = text
}

fun ComponentButtonFilledBinding.getText() = filledButton.text.toString()