package ir.ayantech.pishkhansdk.helper

import android.graphics.PorterDuff
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

fun AppCompatImageView.setTint(color: Int) {
    //For Vector Drawable
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}
