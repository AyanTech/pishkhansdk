package ir.ayantech.pishkhan24.model.api

import android.os.Build
import ir.ayantech.pishkhan24.model.app_logic.Products
import ir.ayantech.pishkhan24.model.app_logic.getProductSimpleAnalyticsEventName
import ir.ayantech.pishkhan24.model.app_logic.getProductSimpleAnalyticsName
import ir.ayantech.pishkhan24.ui.base.AyanActivity
import ir.ayantech.pishkhan24.ui.base.AyanFragment
import ir.ayantech.pishkhan24.ui.bottom_sheet.OptionsSelectionBottomSheet
import ir.ayantech.pishkhan24.ui.dialog.OtpDialog
import ir.ayantech.pishkhansdk.mdoel.OTP
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
    // @Transient
    //var otpDialog: OtpDialog? = null

    fun checkPrerequisites(
        ayanActivity: AyanActivity<*>,
        input: BaseInputModel,
        product: String? = null,
        eventItems: String? = null,
        checkCompletedCallback: (Any?) -> Unit
    ) {
        (ayanActivity.getTopFragment() as? AyanFragment)?.apply {
            if (Prerequisites.isNotNull()) {
                when {
                    Prerequisites?.OTP != null -> {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            (ayanActivity.getTopFragment() as? AyanFragment)?.startSmsReader()
                        }
                        otpDialog =
                            OtpDialog(context = ayanActivity, otp = Prerequisites.OTP) { otpCode ->
                                otpDialog?.dismiss()
                                input.OTPCode = otpCode
                                checkCompletedCallback(input)
                            }
                        otpDialog?.show()

                        if (product != Products.telecomRegisteredLinesProduct.name)
                            AnalyticsHelper.reportAnalyticsEvent(
                                eventName = "start_${product?.getProductSimpleAnalyticsEventName()}_success",
                                product = product?.getProductSimpleAnalyticsName(),
                                eventItems = eventItems
                            )

                    }

                    !Prerequisites?.TaxGroups.isNullOrEmpty() -> {
                        OptionsSelectionBottomSheet(
                            context = ayanActivity,
                            title = "نوع خودرو را انتخاب کنید",
                            null,
                            options = Prerequisites?.TaxGroups!!.map {
                                Type(it.Key, it.Value ?: "")
                            }) {
                            input.TaxGroup = it.Name
                            checkCompletedCallback(input)
                        }.show()
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
}
