package ir.ayantech.pishkhansdk.bottom_sheets

import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.databinding.BottomSheetWaiterBinding
import ir.ayantech.pishkhansdk.ui.bottom_sheets.BaseBottomSheet
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.delayed
import ir.ayantech.whygoogle.helper.trying

class WaiterBottomSheet(activity: WhyGoogleActivity<*>) : BaseBottomSheet<BottomSheetWaiterBinding>(activity) {

    private var progressBarCount = 0

    override fun onCreate() {
        super.onCreate()
        setCancelable(false)
        setCanceledOnTouchOutside(false)

    }

    fun showDialog() {
        progressBarCount++
        delayed(150) {
            if (progressBarCount > 0) {
                show()
            }
        }
    }

    fun hideDialog() {
        progressBarCount--
        delayed(50) {
            trying {
                if (progressBarCount == 0) {
                    dismiss()
                }
            }
        }
    }

    override val binder: (LayoutInflater) -> BottomSheetWaiterBinding
        get() = BottomSheetWaiterBinding::inflate
}