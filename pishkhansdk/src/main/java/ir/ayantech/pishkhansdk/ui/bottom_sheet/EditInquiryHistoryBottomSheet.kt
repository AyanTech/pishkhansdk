package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetEditInquiryHistoryBinding
import ir.ayantech.pishkhansdk.ui.components.changeEnable
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.components.getText
import ir.ayantech.pishkhansdk.ui.components.setAfterTextChangesListener
import ir.ayantech.pishkhansdk.ui.components.setText
import ir.ayantech.whygoogle.helper.StringCallBack

class EditInquiryHistoryBottomSheet(
    context: Context,
    private val note: String?,
    private val onConfirmClicked: StringCallBack
) : BaseBottomSheet<PishkhansdkBottomSheetEditInquiryHistoryBinding>(
    context
) {

    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetEditInquiryHistoryBinding
        get() = PishkhansdkBottomSheetEditInquiryHistoryBinding::inflate

    override val title: String
        get() = getString(R.string.edit_info)

    override fun onCreate() {
        super.onCreate()

        accessViews {
            note?.let {
                dataInputComponent.setText(it)
            }

            cancelBtnComponent.init(
                context = context,
                btnText = getString(R.string.cancel)
            ) {
                dismiss()
            }

            dataInputComponent.init(context = context)
            dataInputComponent.setAfterTextChangesListener {
                confirmBtnComponent.changeEnable(enable = it.isNotEmpty() && it != note)
            }

            confirmBtnComponent.init(
                btnText = getString(R.string.confirm)
            ) {
                onConfirmClicked(dataInputComponent.getText())
                dismiss()
            }
        }
    }
}