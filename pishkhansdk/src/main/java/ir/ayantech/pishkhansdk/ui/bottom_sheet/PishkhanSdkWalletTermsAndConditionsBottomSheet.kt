package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetWalletTermsAndConditionsBinding
import ir.ayantech.pishkhansdk.ui.components.init

open class PishkhanSdkWalletTermsAndConditionsBottomSheet(
    context: Context,
    private val termsAndConditions: String?
): BaseBottomSheet<PishkhansdkBottomSheetWalletTermsAndConditionsBinding>(
    context
) {
    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetWalletTermsAndConditionsBinding
        get() = PishkhansdkBottomSheetWalletTermsAndConditionsBinding::inflate

    override val title: String?
        get() = getString(R.string.rules_and_condition)

    override fun onCreate() {
        super.onCreate()

        accessViews {
            termsAndConditionsTv.text = termsAndConditions

            confirmBtnComponent.init(
                btnText = getString(R.string.confirm)
            ) {
                dismiss()
            }
        }
    }
}