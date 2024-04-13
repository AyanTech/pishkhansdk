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
import ir.ayantech.pishkhansdk.databinding.ActivityMainBinding
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.bottom_sheets.WaiterBottomSheet
import ir.ayantech.pishkhansdk.helper.PishkhanSDK.login
import ir.ayantech.pishkhansdk.helper.PishkhanSDK.serviceName
import ir.ayantech.pishkhansdk.model.api.BankChequeStatusSayad
import ir.ayantech.pishkhansdk.model.api.PostPackagesStatus
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCar
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCarSummary
import ir.ayantech.pishkhansdk.model.api.V2BankIbanInfo
import ir.ayantech.pishkhansdk.model.app_logic.IbanResult
import ir.ayantech.pishkhansdk.model.app_logic.ProductItemDetail
import ir.ayantech.pishkhansdk.model.constants.Parameter.AccountNumber
import ir.ayantech.pishkhansdk.model.constants.Parameter.AccountType
import ir.ayantech.pishkhansdk.model.constants.Parameter.PurchaseKey
import ir.ayantech.pishkhansdk.model.constants.Parameter.TrackingCode
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

        (application as? SDKApplication)?.coreAyanApi?.commonCallStatus =
            ayanCommonCallingStatus
        (application as? SDKApplication)?.servicesAyanApi?.commonCallStatus =
            ayanCommonCallingStatus

        val corePishkhan24Api: AyanApi = (applicationContext as? SDKApplication)?.coreAyanApi!!
        val servicesPishkhan24Api: AyanApi =
            (applicationContext as? SDKApplication)?.servicesAyanApi!!


        waiterBottomSheet = WaiterBottomSheet(this, "")

        servicesPishkhan24Api.commonCallStatus =
            ayanCommonCallingStatus
        corePishkhan24Api.commonCallStatus =
            ayanCommonCallingStatus

        PishkhanSDK.handleUserSession(
            application = "VasHookTrafficFinesInquiry", origin = "cafebazaar",
            platform = "android",
            version = "20.0.0",
            activity = this,
            successCallback = {

            }
        )

        if (intent != null)
            handleIntent()

        binding.inquiryBtn.init("استعلام", btnOnClick = {
           login("09395099494", null, loginIsSuccessful = {
                Toast.makeText(PishkhanUser.context, "loginnn", Toast.LENGTH_LONG)
                    .show()
            })

/*            PishkhanSDK.onInquiryButtonClicked(
                inputModel =TrafficFinesCarSummary.Input(
                    PlateNumber = "09395099494",
                    OTPCode = null,
                    PurchaseKey = null
                ),
                product = ProductItemDetail.InquiryTrafficFinesCarSummary,
                failureCallBack = {
                    Toast.makeText(this, "failure1", Toast.LENGTH_LONG).show()
                },
                handleResultCallback = {
                    Toast.makeText(this, "result successful1", Toast.LENGTH_LONG).show()
                    Log.d(
                        "handleOutput",
                        (it.Result as SubventionHistory.Result).toString()
                    )
                }
            )*/


        })

        binding.inquiryBtn2.init("لاگین", btnOnClick = {
            login("09395099494", binding.enterOtpCodeLayout.getText(), confirmOtpIsSuccessful = {
                Toast.makeText(PishkhanUser.context, "confirmmmm", Toast.LENGTH_LONG)
                    .show()
            })
        })

        /*            PishkhanSDK.onInquiryButtonClicked(
                        inputModel = TrafficFinesCarSummary.Input(
                            PlateNumber = "71-و-741-40",
                            OTPCode = null,
                            PurchaseKey = null
                        ),
                        product = ProductItemDetail.InquiryTrafficFinesCarSummary,
                        failureCallBack = {
                            Toast.makeText(this, "failure1", Toast.LENGTH_LONG).show()
                        },
                        handleResultCallback = {
                            Toast.makeText(this, "result successful1", Toast.LENGTH_LONG).show()
                            Log.d(
                                "handleOutput",
                                (it.Result as TrafficFinesCarSummary.TrafficFinesCarSummaryResult).toString()
                            )
                        }
                    )*/


        /*        PishkhanSDK.getInquiryHistory(
                    context = this,
                    product = ProductItemDetail.InquiryTrafficFinesCarSummary,
                    inquiryHistoryRv = binding.historyRv
                ) {
                    Log.d("inquiry history", it.toString())
                }


                PishkhanSDK.getUserTransactionHistory(
                    userTransactionHistoryRv = binding.historyRv,
                    hasTransactionHistory = {
                        Log.d("hsdbcakf", if (it) "دارد" else "ندارد")
                    }
                ) { output, serviceName ->
                    //  Log.d("hsdbcakf", "${output.Result}   $serviceName")
                }*/

    }


    private fun handleIntent() {
        PishkhanSDK.userPaymentIsSuccessful(
            intent = intent,
        ) { output, serviceName ->
            Toast.makeText(this, "result successful2", Toast.LENGTH_LONG).show()
            Log.d(
                "handleOutput",
                (output.Result as SubventionHistory.Result).toString()
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        (application as SDKApplication).coreAyanApi?.commonCallStatus =
            ayanCommonCallingStatus
        (application as SDKApplication).servicesAyanApi?.commonCallStatus =
            ayanCommonCallingStatus

    }
}