package ir.ayantech.pishkhansdk.ui.components

import android.os.SystemClock
import android.view.View
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentButtonFilledBinding

private var mLastClickTime: Long = 0

fun PishkhansdkComponentButtonFilledBinding.init(
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

fun PishkhansdkComponentButtonFilledBinding.changeEnable(
    enable: Boolean
) {
    filledButton.isEnabled = enable
}

fun PishkhansdkComponentButtonFilledBinding.setText(text: String?) {
    filledButton.text = text
}

fun PishkhansdkComponentButtonFilledBinding.getText() = filledButton.text.toString()