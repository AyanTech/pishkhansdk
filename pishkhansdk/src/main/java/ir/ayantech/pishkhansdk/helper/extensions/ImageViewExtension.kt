package ir.ayantech.pishkhansdk.helper.extensions

import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayInputStream
import androidx.core.net.toUri

fun AppCompatImageView.setTint(@ColorRes color: Int) {
    //For Vector Drawable
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}

fun AppCompatImageView.loadFromString(
    source: String,
    requestOptions: RequestOptions? = null,
    requestListener: RequestListener<Drawable>? = null
) {
    if (source.startsWith("http")) {
        this.loadUrl(source, requestListener = requestListener, requestOptions = requestOptions)
    } else {
        this.loadBase64(source)
    }
}

fun AppCompatImageView.loadBase64(base64: String?) {
    if (base64 == null)
        return
    this.setImageBitmap(
        BitmapFactory.decodeStream(
            ByteArrayInputStream(
                Base64.decode(
                    base64, Base64.DEFAULT
                )
            )
        )
    )
}


fun AppCompatImageView.loadUrl(
    url: String,
    placeHolderResourceId: Int? = null,
    requestOptions: RequestOptions? = null,
    requestListener: RequestListener<Drawable>? = null
) {
    val myOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .timeout(20000)
        .skipMemoryCache(true)
    placeHolderResourceId?.let { myOptions.placeholder(it) }
    val builder = Glide.with(context).load(url.toUri()).apply(requestOptions ?: myOptions)
    if (requestListener != null) {
        builder.addListener(requestListener)
    }
    builder.into(this)
}