package ir.ayantech.pishkhansdk.helper.extensions

import android.os.Build
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ir.ayantech.whygoogle.helper.trying

fun View.changeEnable(isEnable: Boolean, disableAlpha: Float = 0.5f) {
    this.isEnabled = isEnable
    this.alpha = if (isEnable) 1f else disableAlpha
}

fun View.changeAlpha(alpha: Float, isEnable: Boolean = true, setForegroundDrawable: Boolean = false, @DrawableRes foregroundDisable: Int) {
    this.isEnabled = isEnable
    this.alpha = alpha
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.foreground = if (setForegroundDrawable) ContextCompat.getDrawable(this.context, foregroundDisable) else null
    } else {
        trying {
            if (setForegroundDrawable) {
                ContextCompat.getDrawable(this.context, foregroundDisable)?.let { drawable ->
                    drawable.setBounds(0, 0, this.width, this.height)
                    this.overlay.add(drawable)
                }
            } else {
                this.overlay.clear()
            }
        }
    }
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}