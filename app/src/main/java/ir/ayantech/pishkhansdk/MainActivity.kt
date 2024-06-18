package ir.ayantech.pishkhansdk


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCommonCallStatus
import ir.ayantech.ayannetworking.api.CallingState
import ir.ayantech.ayannetworking.ayanModel.FailureType
import ir.ayantech.pishkhansdk.model.api.VehicleThirdPartyInsuranceStatus
import ir.ayantech.pishkhansdk.databinding.ActivityMainBinding
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.bottom_sheets.WaiterBottomSheet
import ir.ayantech.pishkhansdk.model.api.FreewayTollBills
import ir.ayantech.pishkhansdk.model.api.MunicipalityCarAnnualTollBills
import ir.ayantech.pishkhansdk.model.api.PassportStatus
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCar
import ir.ayantech.pishkhansdk.model.api.VehicleThirdPartyInsurance
import ir.ayantech.pishkhansdk.model.app_logic.ProductItemDetail
import ir.ayantech.pishkhansdk.model.constants.Parameter.EngineNumber
import ir.ayantech.pishkhansdk.model.constants.Parameter.PurchaseKey
import ir.ayantech.pishkhansdk.model.constants.Parameter.VIN
import ir.ayantech.pishkhansdk.ui.components.getText
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.activity.WhyGoogleActivity


class MainActivity : WhyGoogleActivity<ActivityMainBinding>() {

    private var waiterBottomSheet: WaiterBottomSheet? = null
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


    override val binder: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    override val containerId: Int = R.id.fragmentContainerFl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as? SDKApplication)?.corePishkhan24Api?.commonCallStatus =
            ayanCommonCallingStatus
        (application as? SDKApplication)?.servicesPishkhan24Api?.commonCallStatus =
            ayanCommonCallingStatus

        val corePishkhan24Api: AyanApi =
            (applicationContext as? SDKApplication)?.corePishkhan24Api!!
        val servicesPishkhan24Api: AyanApi =
            (applicationContext as? SDKApplication)?.servicesPishkhan24Api!!


        waiterBottomSheet = WaiterBottomSheet(this, "")

        servicesPishkhan24Api.commonCallStatus =
            ayanCommonCallingStatus
        corePishkhan24Api.commonCallStatus =
            ayanCommonCallingStatus

        PishkhanSDK.handleUserSession(
            application = "passport", origin = "cafebazaar",
            platform = "android",
            version = "2.0.0",
            activity = this,
            successCallback = {

            }
        )

        if (intent != null)
            handleIntent()

        binding.inquiryBtn2.init("لاگین", btnOnClick = {

/*
            PishkhanSDK.login("09158678539", null, loginIsSuccessful = {
                Toast.makeText(PishkhanUser.context, "loginnn", Toast.LENGTH_LONG)
                    .show()
            })
*/

        })

        binding.inquiryBtn.init("استعلام", btnOnClick = {
/*
            PishkhanSDK.login(
                "09158678539",
                binding.enterOtpCodeLayout.getText(),
                confirmOtpIsSuccessful = {
                    Toast.makeText(PishkhanUser.context, "confirmmmm", Toast.LENGTH_LONG)
                        .show()
                })*/

            PishkhanUser.token = "D89E2819E74D4053990DE94DFF0DD090"
            PishkhanSDK.onInquiryButtonClicked(
                inputModel = VehicleThirdPartyInsurance.Input(
                    NationalCode = "4271158534",
                    InsuranceUniqueCode = "10481923720",
                    OTPCode = null,
                    PurchaseKey = null,
                ),
                product = ProductItemDetail.InquiryThirdPartyInsuranceCar,
                failureCallBack = {
                    Toast.makeText(this, "failure1", Toast.LENGTH_LONG).show()
                },
                handleResultCallback = {
                    Log.d(
                        "asdkjalkdj",
                        "InquiryAnnualTollsCar: ${it as FreewayTollBills.Output}"
                    )
                })
        })


        /*
                PishkhanSDK.getInquiryHistory(
                    context = this,
                    product = ProductItemDetail.InquiryTrafficFinesCarSummary,
                    inquiryHistoryRv = binding.historyRv,
                    hasInquiryHistory = {

                    },
                    handleInquiryHistoryClick = {
                        //User has clicked on item list
                    },
                    failureCallBack = {
                        //Custom failure
                    },
                    changeStatusCallback = {
                        //Custom change status
                    }
                )
        */


        /*        PishkhanSDK.getUserTransactionHistory(
                    serviceName = ProductItemDetail.InquiryThirdPartyInsuranceCar,
                    userTransactionHistoryRv = binding.historyRv,
                    hasTransactionHistory = {
                        Log.d("hsdbcakf", if (it) "دارد" else "ندارد")
                    }
                ) { output, serviceName ->
                    Log.d("hsdbcakf", "${output.Result}   $serviceName")
                }*/

    }


    private fun handleIntent() {
        PishkhanSDK.userPaymentIsSuccessful(
            intent = intent,
        ) { output, serviceName ->
            Toast.makeText(this, "result successful2", Toast.LENGTH_LONG).show()
            Log.d(
                "handleOutput",
                (output.Result as TrafficFinesCar.TrafficFineResult).toString()
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        (application as SDKApplication).corePishkhan24Api?.commonCallStatus =
            ayanCommonCallingStatus
        (application as SDKApplication).servicesPishkhan24Api?.commonCallStatus =
            ayanCommonCallingStatus

    }
}