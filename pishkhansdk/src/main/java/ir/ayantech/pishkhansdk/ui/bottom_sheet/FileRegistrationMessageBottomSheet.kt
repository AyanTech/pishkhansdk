package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetFileRegistrationMessageBinding
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.helper.BooleanCallBack
import ir.ayantech.whygoogle.helper.SimpleCallBack

class FileRegistrationMessageBottomSheet(
    context: Context,
    private val description: String,
    private val onFileRegistrationViaPishkhanClicked: SimpleCallBack
): BaseBottomSheet<PishkhansdkBottomSheetFileRegistrationMessageBinding>(
    context
) {

    override val title: String?
        get() = null

    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetFileRegistrationMessageBinding
        get() = PishkhansdkBottomSheetFileRegistrationMessageBinding::inflate

    override fun onCreate() {
        super.onCreate()

        accessViews {
            confirmFileRegistrationBtnComponent.init(
                btnText = getString(R.string.car_annual_tax_file_registration_message_button_text)
            ) {
                dismiss()
                onFileRegistrationViaPishkhanClicked()
            }

            descriptionTv.text = description
        }
    }
}