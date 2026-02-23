package ir.ayantech.pishkhansdk.ui.components

import android.os.SystemClock
import android.view.View
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentButtonFilledBinding

private var mLastClickTime: Long = 0
private var minAcceptableValue: Long = 100000
fun PishkhansdkComponentButtonFilledBinding.init(
    btnText: String,
    isEnable: Boolean = true,
    minValue: Long? = null,
    btnOnClick: View.OnClickListener? = null
) {
    filledButton.text = btnText
    filledButton.setOnClickListener {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return@setOnClickListener
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        btnOnClick?.onClick(it)
    }
    changeEnable(enable = isEnable)
    filledButton.setHorizontallyScrolling(true)
    filledButton.isSelected = true
      minValue?.let {
          minAcceptableValue = it
    }
}

fun PishkhansdkComponentButtonFilledBinding.changeEnable(
    enable: Boolean
) {
    filledButton.isEnabled = enable
}

fun PishkhansdkComponentButtonFilledBinding.checkVisibilityStatus(intValue: Long?) {
    val isEnabled = intValue?.let { it >= minAcceptableValue } ?: false
    this.changeEnable(isEnabled)
}
fun PishkhansdkComponentButtonFilledBinding.setText(text: String?) {
    filledButton.text = text
}

fun PishkhansdkComponentButtonFilledBinding.getText() = filledButton.text.toString()

fun PishkhansdkComponentButtonFilledBinding.performClick() {
    filledButton.performClick()
}