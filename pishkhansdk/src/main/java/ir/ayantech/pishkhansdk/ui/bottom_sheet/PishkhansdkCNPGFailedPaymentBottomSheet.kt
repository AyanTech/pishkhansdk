package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetCnpgFailedPaymentBinding
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.helper.SimpleCallBack

open class PishkhansdkCNPGFailedPaymentBottomSheet(
    context: Context,
    private val failureMessage: String
): BaseBottomSheet<PishkhansdkBottomSheetCnpgFailedPaymentBinding>(
    context
) {

    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetCnpgFailedPaymentBinding
        get() = PishkhansdkBottomSheetCnpgFailedPaymentBinding::inflate

    override val title: String?
        get() = null

    override fun onCreate() {
        super.onCreate()

        accessViews {

            descriptionTv.text = failureMessage

            returnButtonComponent.init(
                btnText = getString(R.string.return_back)
            ) {
                onCloseIvClicked()
            }
        }
    }
}