package ir.ayantech.pishkhansdk


import android.os.Bundle
import android.view.LayoutInflater
import com.alirezabdn.whyfinal.BuildConfig
import com.google.gson.GsonBuilder
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCommonCallStatus
import ir.ayantech.ayannetworking.api.CallingState
import ir.ayantech.ayannetworking.ayanModel.FailureType
import ir.ayantech.ayannetworking.ayanModel.LogLevel
import ir.ayantech.pishkhansdk.databinding.ActivityMainBinding
import ir.ayantech.pishkhansdk.mdoel.JusticeSharesPortfolio
import ir.ayantech.pishkhansdk.ui.fragments.AyanFragment
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import java.lang.reflect.Modifier


class MainActivity : WhyGoogleActivity<ActivityMainBinding>() {

    var servicesAyanApi: AyanApi? = null
    var coreAyanApi: AyanApi? = null

    override val binder: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    override val containerId: Int = R.id.fragmentContainerFl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create()

        servicesAyanApi = AyanApi(
            context = this,
            getUserToken = { "B258B6B796CB46A0B84B4695355A5B96" },
            defaultBaseUrl = "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )

        coreAyanApi = AyanApi(
            context = this,
            getUserToken = { "B258B6B796CB46A0B84B4695355A5B96" },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/core.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )

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


        coreAyanApi?.commonCallStatus =
            ayanCommonCallingStatus
        servicesAyanApi?.commonCallStatus =
            ayanCommonCallingStatus



        val servicesPishkhan24Api = servicesAyanApi
        val corePishkhan24Api = coreAyanApi


        binding.inquiryBtn.setOnClickListener {
            if (servicesPishkhan24Api != null && corePishkhan24Api != null) {
                onInquiryButtonClicked(
                    activity = this,
                    inputModel = JusticeSharesPortfolio.Input(
                        NationalCode = "5230069570", OTPCode = null,
                        PurchaseKey = null
                    ),
                    productName = "v1_InquiryJusticeSharesPortfolio",
                    servicesPishkhan24Api = servicesPishkhan24Api,
                    corePishkhan24Api = corePishkhan24Api
                ) {

                }
            }
        }
    }
}