package ir.ayantech.pishkhansdk.model.app_logic

import ir.ayantech.pishkhansdk.helper.PaymentHelper.otpDialog
import ir.ayantech.pishkhansdk.ui.dialogs.OtpDialog
import ir.ayantech.pishkhansdk.ui.fragments.AyanFragment
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
        product: String? = null,
        checkCompletedCallback: (Any?) -> Unit
    ) {
        if (Prerequisites.isNotNull()) {
            when {
                Prerequisites?.OTP != null -> {
                    otpDialog =
                        OtpDialog(context = ayanActivity, otp = Prerequisites.OTP) { otpCode ->
                            otpDialog?.dismiss()
                            input.OTPCode = otpCode
                            checkCompletedCallback(input)
                        }
                    otpDialog?.show()


                }

                !Prerequisites?.TaxGroups.isNullOrEmpty() -> {
                    /*                        OptionsSelectionBottomSheet(
                                                context = ayanActivity,
                                                title = "نوع خودرو را انتخاب کنید",
                                                null,
                                                options = Prerequisites?.TaxGroups!!.map {
                                                    Type(it.Key, it.Value ?: "")
                                                }) {
                                                input.TaxGroup = it.Name
                                                checkCompletedCallback(input)
                                            }.show()*/
                }

                else -> {
                    otpDialog?.dismiss()
                    checkCompletedCallback(null)
                }
            }
        } else {
            otpDialog?.dismiss()
            checkCompletedCallback(null)
        }

    }
}
