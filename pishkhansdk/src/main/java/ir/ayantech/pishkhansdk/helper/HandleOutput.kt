package ir.ayantech.pishkhansdk.helper

import android.util.Log
import android.widget.Toast
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanApiCallback
import ir.ayantech.networking.callJusticeSharesPortfolio
import ir.ayantech.networking.simpleCallJusticeSharesPortfolio
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.api.JusticeSharesPortfolio
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull

object HandleOutput {

    fun handleJusticeSharesPortfolioOutput(
        activity: WhyGoogleActivity<*>,
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: JusticeSharesPortfolio.Output?) -> Unit)? = null
    ) {
        servicesPishkhan24Api.callJusticeSharesPortfolio(
            input = input as JusticeSharesPortfolio.Input
        ) {
            success { output ->
                output?.checkPrerequisites(activity, input) {
                    if (it.isNull()) {
                        handleResultCallback?.invoke(output)
                    } else {
                        (it as? JusticeSharesPortfolio.Input)?.let {
                            handleJusticeSharesPortfolioOutput(
                                activity,
                                apiCalledFromTransactionsFragment,
                                it,
                                servicesPishkhan24Api
                            )
                        }
                    }
                }
                failure {
                    Toast.makeText(activity, "failure: $it", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

