package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetConfirmationBinding
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.helper.SimpleCallBack

class ConfirmationBottomSheet(
    context: Context,
    @StringRes private val description: Int = R.string.delete_confirmation,
    private val onConfirmClicked: SimpleCallBack
) : BaseBottomSheet<PishkhansdkBottomSheetConfirmationBinding>(context) {

    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetConfirmationBinding
        get() = PishkhansdkBottomSheetConfirmationBinding::inflate

    override val title: String
        get() = getString(R.string.attention)

    override fun onCreate() {
        super.onCreate()

        accessViews {
            descriptionTv.text = getString(description)

            cancelBtnComponent.init(
                context = context,
                btnText = getString(R.string.cancel)
            ) {
                dismiss()
            }

            confirmBtnComponent.init(
                btnText = getString(ir.ayantech.pishkhansdk.R.string.confirm)
            ) {
                dismiss()
                onConfirmClicked()
            }
        }
    }
}