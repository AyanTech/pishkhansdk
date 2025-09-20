package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetCnpgSuccessfulPaymentBinding
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.helper.SimpleCallBack

open class PishkhansdkCNPGSuccessfulPaymentBottomSheet(
    context: Context,
    private val onReturnButtonClicked: SimpleCallBack
): BaseBottomSheet<PishkhansdkBottomSheetCnpgSuccessfulPaymentBinding>(
    context
) {

    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetCnpgSuccessfulPaymentBinding
        get() = PishkhansdkBottomSheetCnpgSuccessfulPaymentBinding::inflate

    override val title: String?
        get() = null

    override val isCancelable: Boolean
        get() = false

    override val isDraggable: Boolean
        get() = false

    override val hasCloseOption: Boolean
        get() = false

    override fun onCreate() {
        super.onCreate()

        accessViews {
            returnButtonComponent.init(
                btnText = getString(R.string.return_back)
            ) {
                dismiss()
                onReturnButtonClicked()
            }
        }
    }
}