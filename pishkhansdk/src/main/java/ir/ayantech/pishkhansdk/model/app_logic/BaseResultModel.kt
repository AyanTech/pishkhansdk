package ir.ayantech.pishkhansdk.model.app_logic

import ir.ayantech.pishkhansdk.helper.PaymentHelper.otpBottomSheetDialog
import ir.ayantech.pishkhansdk.ui.bottom_sheet.OptionsSelectionBottomSheet
import ir.ayantech.pishkhansdk.ui.bottom_sheet.OtpBottomSheetDialog
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNotNull

data class Prerequisites(
    val OTP: OTP?,
    val TaxGroups: List<ExtraInfo>?
)

data class Query(
    val AutoFill: Boolean,
    val Favorite: Boolean,
    val Index: String,
    val Note: String,
    val Parameters: List<ExtraInfo>,
    val UniqueID: String
)

data class Messages(
    val Hint: String?,
    val NoContent: String?,
    val Warning: String?
)

open class BaseResultModel<T>(
    val Query: Query,
    val Result: T?,
    val WarningMessage: String?,
    val Prerequisites: Prerequisites?,
    val Messages: Messages?
) {


    fun checkPrerequisites(
        ayanActivity: WhyGoogleActivity<*>,
        input: BaseInputModel,
        checkCompletedCallback: (Any?) -> Unit
    ) {
        if (Prerequisites.isNotNull()) {
            when {
                Prerequisites?.OTP != null -> {
                    otpBottomSheetDialog =
                        OtpBottomSheetDialog(
                            context = ayanActivity,
                            otp = Prerequisites.OTP
                        ) { otpCode ->
                            otpBottomSheetDialog?.dismiss()
                            input.OTPCode = otpCode
                            checkCompletedCallback(input)
                        }
                    otpBottomSheetDialog?.show()


                }

                !Prerequisites?.TaxGroups.isNullOrEmpty() -> {
                    OptionsSelectionBottomSheet(
                        context = ayanActivity,
                        title = "نوع خودرو را انتخاب کنید",
                        descriptionKeyValue = null,
                        options = Prerequisites?.TaxGroups!!.map {
                            Type(it.Key, it.Value ?: "")
                        }) {
                        input.TaxGroup = it.Name
                        checkCompletedCallback(input)
                    }.show()
                }

                else -> {
                    otpBottomSheetDialog?.dismiss()
                    checkCompletedCallback(null)
                }
            }
        } else {
            otpBottomSheetDialog?.dismiss()
            checkCompletedCallback(null)
        }

    }
}