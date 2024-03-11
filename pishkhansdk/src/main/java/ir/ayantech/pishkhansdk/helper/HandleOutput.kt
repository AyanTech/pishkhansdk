package ir.ayantech.pishkhansdk.helper

import android.widget.Toast
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.networking.callJusticeSharesPortfolio
import ir.ayantech.networking.callSubventionHistory
import ir.ayantech.networking.callTrafficFinesCar
import ir.ayantech.networking.callTrafficFinesCarSummary
import ir.ayantech.networking.simpleCallJusticeSharesPortfolio
import ir.ayantech.networking.simpleCallSubventionHistory
import ir.ayantech.networking.simpleCallTrafficFinesCar
import ir.ayantech.networking.simpleCallTrafficFinesCarSummary
import ir.ayantech.pishkhansdk.model.api.InvoiceInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.api.JusticeSharesPortfolio
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCar
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCarSummary
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.constants.Parameter
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.helper.isNull

object HandleOutput {

    fun handleOutputResult(
        invoiceInfoOutput: InvoiceInfo.Output,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        when (invoiceInfoOutput.Invoice.Service.Type.Name) {
            Products.justiceSharesProduct.name -> {
                handleJusticeSharesPortfolioOutput(input = JusticeSharesPortfolio.Input(
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ),
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    }
                )
            }

            Products.subventionHistoryProduct.name -> {
                handleSubventionHistoryOutput(input = SubventionHistory.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ),
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    }
                )
            }

            Products.carTrafficFinesProduct.name -> {
                handleTrafficFinesCarOutput(input = TrafficFinesCar.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ),
                    endPoint = EndPoints.TrafficFinesCar,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    }
                )
            }

            Products.carTrafficFinesSummaryProduct.name -> {
                handleTrafficFinesCarSummaryOutput(input = TrafficFinesCarSummary.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ),
                    endPoint = EndPoints.TrafficFinesCarSummary,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    }
                )
            }

            else -> {}

        }

    }

    fun handleJusticeSharesPortfolioOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallJusticeSharesPortfolio(
            input = input as JusticeSharesPortfolio.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? JusticeSharesPortfolio.Input)?.let {
                        handleJusticeSharesPortfolioOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }


        }
    }

    fun handleSubventionHistoryOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallSubventionHistory(
            input = input as SubventionHistory.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? SubventionHistory.Input)?.let {
                        handleSubventionHistoryOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleTrafficFinesCarOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        endPoint: String,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTrafficFinesCar(
            input = input as TrafficFinesCar.Input,
            endPoint = endPoint
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? TrafficFinesCar.Input)?.let {
                        handleTrafficFinesCarOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                            endPoint = endPoint
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }

        }
    }

    fun handleTrafficFinesCarSummaryOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        endPoint: String,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTrafficFinesCarSummary(
            input = input as TrafficFinesCarSummary.Input,
            endPoint = endPoint
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? TrafficFinesCarSummary.Input)?.let {
                        handleTrafficFinesCarSummaryOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                            endPoint = endPoint
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }
}

