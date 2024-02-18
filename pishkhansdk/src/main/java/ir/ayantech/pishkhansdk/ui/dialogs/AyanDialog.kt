package ir.ayantech.pishkhansdk.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhansdk.R
import ir.ayantech.whygoogle.helper.viewBinding


abstract class AyanDialog<T : ViewBinding>(context: Context) :
    Dialog(context, R.style.AyanDialog) {

    val binding: T by viewBinding(binder)

    abstract val binder: (LayoutInflater) -> T

    open val isCentered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_RTL
        setContentView(binding.root)
        window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this.context,
                R.drawable.back_dialog
            )
        )

        if (!isCentered) {
            window?.setGravity(Gravity.BOTTOM)
            val wl: WindowManager.LayoutParams = window?.attributes!!
            wl.y = 32
            this.onWindowAttributesChanged(wl)
        }
    }
}