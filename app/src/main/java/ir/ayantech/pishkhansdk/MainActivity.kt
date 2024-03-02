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
import ir.ayantech.pishkhansdk.helper.extensions.PishkhanSDK
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.ui.bottom_sheets.WaiterBottomSheet
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
            //getUserToken = { PishkhanSDK.getPishkhanToken() },
            getUserToken = { "B258B6B796CB46A0B84B4695355A5B96" },
            defaultBaseUrl = "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )

        corePishkhan24Api = AyanApi(
            context = this,
            //getUserToken = { PishkhanSDK.getPishkhanToken() },
            getUserToken = { "B258B6B796CB46A0B84B4695355A5B96" },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/core.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )

        PishkhanSDK.initialize(
            context = this, Application = "pishkhan24", Origin = "myket",
            Platform = "android",
            Version = "6.2.1",
            corePishkhan24Api = corePishkhan24Api!!
        )


        waiterBottomSheet = WaiterBottomSheet(this)

        val ayanCommonCallingStatus = AyanCommonCallStatus {
            failure { failure ->
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
/*                when (it) {
                    CallingState.NOT_USED -> waiterBottomSheet?.hideDialog()
                    CallingState.LOADING -> waiterBottomSheet?.showDialog()
                    CallingState.FAILED -> waiterBottomSheet?.hideDialog()
                    CallingState.SUCCESSFUL -> waiterBottomSheet?.hideDialog()
                }*/
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
                    activity = this,
                    inputModel = SubventionHistory.Input(
                        MobileNumber = "09016140723", OTPCode = null,
                        PurchaseKey = null
                    ),
                    serviceName = "v2_InquiryGovernmentSubventionHistory",
                    servicesPishkhan24Api = servicesPishkhan24Api!!,
                    corePishkhan24Api = corePishkhan24Api!!,
                    failureCallBack = {
                        Toast.makeText(this, "failure1", Toast.LENGTH_LONG).show()
                    },
                    handleResultCallback = {
                        Toast.makeText(this, "result successful", Toast.LENGTH_LONG)
                            .show()
                        Log.d("handleOutput", it.Result.toString())
                    }
                )
            }
        }
    }

    private fun handleIntent() {
        if (servicesPishkhan24Api != null && corePishkhan24Api != null) {
            PishkhanSDK.userPaymentIsSuccessful(
                intent = intent,
                corePishkhan24Api = corePishkhan24Api!!,
                servicesPishkhan24Api = servicesPishkhan24Api!!,
                activity = this
            ) {
                Toast.makeText(this, "result successful", Toast.LENGTH_LONG)
                    .show()
                Log.d("handleOutput", it.Result.toString())
            }
        }
    }
}