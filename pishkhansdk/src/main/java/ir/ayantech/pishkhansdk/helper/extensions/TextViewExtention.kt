package ir.ayantech.pishkhansdk.helper.extensions

import android.graphics.Typeface
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ir.ayantech.whygoogle.helper.formatAmount
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.trying

fun TextView.textColor(color: Int) {
    setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.lazySetNumberText(number: Int, duration: Long = 500L) {
    val chunk = 33
    var currentNumber = 0
    val interval = (number - currentNumber) / chunk
    object : CountDownTimer(duration, duration / chunk) {
        override fun onFinish() {
            trying {
                this@lazySetNumberText.text = number.toString()
            }
        }

        override fun onTick(p0: Long) {
            trying {
                currentNumber += interval
                this@lazySetNumberText.text = currentNumber.toString()
            }
        }
    }.start()
}

fun TextView.lazyFormattedSetNumberText(
    number: Int,
    duration: Long = 500L,
    formatText: (Int) -> String
) {
    val chunk = 33
    var currentNumber = 0
    val interval = (number - currentNumber) / chunk
    object : CountDownTimer(duration, duration / chunk) {
        override fun onFinish() {
            trying {
                this@lazyFormattedSetNumberText.text = formatText(number)
            }
        }

        override fun onTick(p0: Long) {
            trying {
                currentNumber += interval
                this@lazyFormattedSetNumberText.text = formatText(currentNumber)
            }
        }
    }.start()
}

fun TextView.setButtonAmount(amount: Long?) {
    when {
        amount == null -> this.text = "0 ریال"
        amount <= 0L -> this.text = "0 ریال"
        else -> this.lazyFormattedSetNumberText(amount.toInt()) {
            it.toLong().formatAmount()
        }
    }
}

fun TextView.setCustomFont(fontName: String) {
    this.typeface = Typeface.createFromAsset(context.assets, fontName)
}

fun TextView.setSpanText(
    text: String,
    start: Int,
    end: Int,
    color: Int,
    bold: Boolean = false,
    size: Int? = null
) {
    val spannable: Spannable = SpannableString(text)

    spannable.setSpan(
        ForegroundColorSpan(color),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    size?.let {
        spannable.setSpan(
            AbsoluteSizeSpan(size),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    this.text = spannable
}

fun TextView.setEndDrawable(@DrawableRes drawable: Int?) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        null,
        null,
        if (drawable.isNotNull()) drawable?.let { context.getDrawableCompat(drawable) } else null,
        null
    )
}