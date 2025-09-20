package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetRemoveBankCardConfirmationBinding
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.helper.SimpleCallBack

open class PishkhansdkRemoveBankCardConfirmationBottomSheet(
    context: Context,
    private val cardNumber: String,
    private val onCardRemoveConfirmed: SimpleCallBack
): BaseBottomSheet<PishkhansdkBottomSheetRemoveBankCardConfirmationBinding>(
    context
) {

    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetRemoveBankCardConfirmationBinding
        get() = PishkhansdkBottomSheetRemoveBankCardConfirmationBinding::inflate

    override val title: String?
        get() = getString(R.string.bank_card_remove)

    override fun onCreate() {
        super.onCreate()

        accessViews {
            descriptionTv.setText(context.getString(R.string.bank_card_remove_confirmation, "\u202A${cardNumber}\u202C"))

            cancelRemoveButtonComponent.init(
                btnText = getString(R.string.cancel)
            ) {
                dismiss()
            }

            removeConfirmedButtonComponent.init(
                context = context,
                btnText = getString(R.string.remove)
            ) {
                onCardRemoveConfirmed()
                dismiss()
            }
        }
    }
}