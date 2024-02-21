package ir.ayantech.pishkhansdk.mdoel

import android.util.Log
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.networking.callJusticeSharesPortfolio
import ir.ayantech.pishkhan24.model.api.BaseInputModel
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull

object handleOutput {

    fun handleJusticeSharesPortfolioOutput(
        activity: WhyGoogleActivity<*>,
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        servicesPishkhan24Api: AyanApi,
    ) {
        servicesPishkhan24Api.callJusticeSharesPortfolio(
            input = input as JusticeSharesPortfolio.Input
        ) {
            success { output ->
                output?.checkPrerequisites(activity, input) {
                    if (it.isNull()) {
                        Log.d("handleOutput", it.toString())
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
            }
            failure {

            }
        }

    }
}