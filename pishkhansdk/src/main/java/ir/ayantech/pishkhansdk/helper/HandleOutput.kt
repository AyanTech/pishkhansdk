package ir.ayantech.pishkhansdk.helper

import android.util.Log
import android.widget.Toast
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanApiCallback
import ir.ayantech.networking.callJusticeSharesPortfolio
import ir.ayantech.networking.callSubventionHistory
import ir.ayantech.networking.simpleCallJusticeSharesPortfolio
import ir.ayantech.pishkhansdk.model.api.InvoiceInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.api.JusticeSharesPortfolio
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.pishkhansdk.model.constants.Parameter
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull

object HandleOutput {

    fun handleOutputResult(
        activity: WhyGoogleActivity<*>,
        invoiceInfoOutput: InvoiceInfo.Output,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        when (invoiceInfoOutput.Invoice.Service.Type.Name) {
            Products.justiceSharesProduct.name -> {
                handleJusticeSharesPortfolioOutput(
                    activity = activity, input = JusticeSharesPortfolio.Input(
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        OTPCode = null,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                    ), servicesPishkhan24Api = servicesPishkhan24Api,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    }
                )
            }

            Products.subventionHistoryProduct.name -> {
                handleSubventionHistoryOutput(
                    activity = activity, input = SubventionHistory.Input(
                        MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                        OTPCode = null,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                    ), servicesPishkhan24Api = servicesPishkhan24Api,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    }
                )
            }

            else -> {}

        }

    }

    fun handleJusticeSharesPortfolioOutput(
        activity: WhyGoogleActivity<*>,
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
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
                                activity = activity,
                                apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                                input = it,
                                servicesPishkhan24Api = servicesPishkhan24Api
                            ) {
                                handleResultCallback?.invoke(output)
                            }
                        }
                    }
                }
            }
            failure {
                Toast.makeText(activity, "failure: $it", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun handleSubventionHistoryOutput(
        activity: WhyGoogleActivity<*>,
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        servicesPishkhan24Api: AyanApi,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        servicesPishkhan24Api.callSubventionHistory(
            input = input as SubventionHistory.Input
        ) {
            success { output ->
                output?.checkPrerequisites(activity, input) {
                    if (it.isNull()) {
                        handleResultCallback?.invoke(output)
                    } else {
                        (it as? SubventionHistory.Input)?.let {
                            handleSubventionHistoryOutput(
                                activity = activity,
                                apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                                input = it,
                                servicesPishkhan24Api = servicesPishkhan24Api
                            ) {
                                handleResultCallback?.invoke(output)
                            }
                        }
                    }
                }
            }
            failure {
                Toast.makeText(activity, "failure: $it", Toast.LENGTH_LONG).show()
            }
        }
    }
}

