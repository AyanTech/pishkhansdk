package ir.ayantech.pishkhansdk.helper.extensions

import android.graphics.PorterDuff
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

fun AppCompatImageView.setTint(@ColorRes color: Int) {
    //For Vector Drawable
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}
