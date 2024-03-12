package ir.ayantech.pishkhansdk


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.alirezabdn.whyfinal.BuildConfig
import com.google.gson.GsonBuilder
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCommonCallStatus
import ir.ayantech.ayannetworking.api.CallingState
import ir.ayantech.ayannetworking.ayanModel.FailureType
import ir.ayantech.ayannetworking.ayanModel.LogLevel
import ir.ayantech.pishkhansdk.databinding.ActivityMainBinding
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.bottom_sheets.WaiterBottomSheet
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.model.api.V1BankIbanInfo
import ir.ayantech.pishkhansdk.model.app_logic.ProductItemDetail
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import java.lang.reflect.Modifier


class MainActivity : WhyGoogleActivity<ActivityMainBinding>() {

    private var servicesPishkhan24Api: AyanApi? = null
    private var corePishkhan24Api: AyanApi? = null
    private var waiterBottomSheet: WaiterBottomSheet? = null

    override val binder: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    override val containerId: Int = R.id.fragmentContainerFl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create()

        servicesPishkhan24Api = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )


        corePishkhan24Api = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/core.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )

        PishkhanSDK.initialize(
            context = this, application = "shebainquiry", origin = "cafebazaar",
            platform = "android",
            version = "4.0.0",
            schema = "bankiban",
            activity = this,
            host = "ir.ayantech.bankiban",
            corePishkhan24Api = corePishkhan24Api!!,
            servicesPishkhan24Api = servicesPishkhan24Api!!,
            successCallback = {

            }
        )


        waiterBottomSheet = WaiterBottomSheet(this, "")

        val ayanCommonCallingStatus = AyanCommonCallStatus {
            failure { failure ->
                Toast.makeText(this@MainActivity, failure.failureMessage, Toast.LENGTH_LONG).show()
                when (failure.failureType) {
                    FailureType.LOGIN_REQUIRED -> {

                    }

                    FailureType.CANCELED -> {
                    }

                    else -> {

                    }
                }
            }
            changeStatus {
                when (it) {
                    CallingState.NOT_USED -> waiterBottomSheet?.hideDialog()
                    CallingState.LOADING -> waiterBottomSheet?.showDialog()
                    CallingState.FAILED -> waiterBottomSheet?.hideDialog()
                    CallingState.SUCCESSFUL -> waiterBottomSheet?.hideDialog()
                }
            }
        }

        servicesPishkhan24Api?.commonCallStatus =
            ayanCommonCallingStatus
        corePishkhan24Api?.commonCallStatus =
            ayanCommonCallingStatus

        if (intent != null)
            handleIntent()

        binding.inquiryBtn.setOnClickListener {
            if (servicesPishkhan24Api != null && corePishkhan24Api != null) {
                PishkhanSDK.onInquiryButtonClicked(
                    inputModel = V1BankIbanInfo.Input(
                        AccountType = "Deposit",
                        Iban = "IR080120020000009332198720",
                        OTPCode = null,
                        PurchaseKey = null
                    ),
                    product = ProductItemDetail.InquiryAccountNumberByIban,
                    failureCallBack = {
                        Toast.makeText(this, "failure1", Toast.LENGTH_LONG).show()
                    },
                    handleResultCallback = {
                        Toast.makeText(this, "result successful1", Toast.LENGTH_LONG).show()
                        Log.d("handleOutput", it.Result.toString())
                    }
                )

            }

        }

        PishkhanSDK.getInquiryHistory(
            context = this,
            product = "v2_InquiryGovernmentSubventionHistory",
            inquiryHistoryRv = binding.historyRv
        ) {
            Log.d("inquiry history", it.toString())
        }


        /*        PishkhanSDK.getUserTransactionHistory(
                    userTransactionHistoryRv = binding.historyRv
                ) { output, serviceName ->
                    Log.d("hsdbcakf", "${output.Result}   $serviceName")
                }*/
    }

    private fun handleIntent() {
        PishkhanSDK.userPaymentIsSuccessful(
            intent = intent,
        ) {
            Toast.makeText(this, "result successful2", Toast.LENGTH_LONG).show()
            Log.d("handleOutput", it.toString())
        }
    }
}