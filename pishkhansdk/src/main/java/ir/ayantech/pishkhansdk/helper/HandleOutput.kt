package ir.ayantech.pishkhansdk.helper


import ir.ayantech.networking.simpleCallBankChequeStatusSayad
import ir.ayantech.networking.simpleCallFreewayTollBills
import ir.ayantech.networking.simpleCallJusticeSharesPortfolio
import ir.ayantech.networking.simpleCallPostPackagesStatus
import ir.ayantech.networking.simpleCallSubventionHistory
import ir.ayantech.networking.simpleCallTrafficFinesCar
import ir.ayantech.networking.simpleCallTrafficFinesCarSummary
import ir.ayantech.networking.simpleCallV2BankIbanInfo
import ir.ayantech.networking.simpleCallV3BankIbanInfo
import ir.ayantech.pishkhansdk.model.api.BankChequeStatusSayad
import ir.ayantech.pishkhansdk.model.api.FreewayTollBills
import ir.ayantech.pishkhansdk.model.api.InvoiceInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.api.JusticeSharesPortfolio
import ir.ayantech.pishkhansdk.model.api.PostPackagesStatus
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCar
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCarSummary
import ir.ayantech.pishkhansdk.model.api.V1BankIbanInfo
import ir.ayantech.pishkhansdk.model.api.V2BankIbanInfo
import ir.ayantech.pishkhansdk.model.api.V3BankIbanInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.constants.Parameter
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
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.subventionHistoryProduct.name -> {
                handleSubventionHistoryOutput(input = SubventionHistory.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.carTrafficFinesProduct.name -> {
                handleTrafficFinesCarOutput(input = TrafficFinesCar.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesCar, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.carTrafficFinesSummaryProduct.name -> {
                handleTrafficFinesCarSummaryOutput(input = TrafficFinesCarSummary.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesCarSummary, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.motorTrafficFinesProduct.name -> {
                handleTrafficFinesCarOutput(input = TrafficFinesCar.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesMotorcycle, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.motorTrafficFinesSummeryProduct.name -> {
                handleTrafficFinesCarSummaryOutput(input = TrafficFinesCarSummary.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesMotorcycleSummary, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.ibanByCardNumberProduct.name -> {
                handleIbanByCardNumberOutput(input = V3BankIbanInfo.Input(
                    AccountType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountType }.Value,
                    CardNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.CardNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.ibanByAccountNumberProduct.name -> {
                handleIbanByAccountNumberOutput(input = V2BankIbanInfo.Input(
                    AccountType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountType }.Value,
                    AccountNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountNumber }.Value,
                    Bank = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Bank }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.accountNumberByIbanProduct.name -> {
                handleAccountNumberByIbanOutput(input = V1BankIbanInfo.Input(
                    AccountType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountType }.Value,
                    Iban = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Iban }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.postPackageTrackingProduct.name -> {
                handlePostPackageTrackingOutput(input = PostPackagesStatus.Input(
                    TrackingCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.TrackingCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.sayadChequeProduct.name -> {
                handleBankChequeStatusSayadOutput(input = BankChequeStatusSayad.Input(
                    ChequeNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.ChequeNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.freewayTollBillsProduct.name -> {
                handleFreewayTollBillsOutput(input = FreewayTollBills.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
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
            input = input as TrafficFinesCar.Input, endPoint = endPoint
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
            input = input as TrafficFinesCarSummary.Input, endPoint = endPoint
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

    fun handleIbanByCardNumberOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCall<V3BankIbanInfo.Output>(
            input = input as V3BankIbanInfo.Input, endPoint = EndPoints.v3BankIbanInfo
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? V3BankIbanInfo.Input)?.let {
                        handleIbanByCardNumberOutput(
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

    fun handleIbanByAccountNumberOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCall<V3BankIbanInfo.Output>(
            input = input as V2BankIbanInfo.Input, endPoint = EndPoints.v2BankIbanInfo
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? V2BankIbanInfo.Input)?.let {
                        handleIbanByAccountNumberOutput(
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

    fun handleAccountNumberByIbanOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCall<V3BankIbanInfo.Output>(
            input = input as V1BankIbanInfo.Input, endPoint = EndPoints.v1BankIbanInfo
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? V1BankIbanInfo.Input)?.let {
                        handleAccountNumberByIbanOutput(
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

    fun handlePostPackageTrackingOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallPostPackagesStatus(
            input = input as PostPackagesStatus.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? PostPackagesStatus.Input)?.let {
                        handlePostPackageTrackingOutput(
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

    fun handleBankChequeStatusSayadOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallBankChequeStatusSayad(
            input = input as BankChequeStatusSayad.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? BankChequeStatusSayad.Input)?.let {
                        handleBankChequeStatusSayadOutput(
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

    fun handleFreewayTollBillsOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallFreewayTollBills(
            input = input as FreewayTollBills.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? FreewayTollBills.Input)?.let {
                        handleFreewayTollBillsOutput(
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
}
