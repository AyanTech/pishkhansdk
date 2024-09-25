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
import ir.ayantech.pishkhansdk.bottom_sheets.WaiterBottomSheet
import ir.ayantech.pishkhansdk.databinding.ActivityMainBinding
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.model.api.CarPlateNumberHistory
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCar
import ir.ayantech.pishkhansdk.model.api.VehicleAuthenticity
import ir.ayantech.pishkhansdk.model.app_logic.ProductItemDetail
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
    override val containerId: Int
        get() = R.id.containerFl

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
            application = "VasHookTrafficFinesInquiry",
            origin = "cafebazaar",
            platform = "android",
            version = "20.0.0",
            activity = this,
            successCallback = {
                if (PishkhanUser.phoneNumber.isEmpty()) {
                    PishkhanSDK.login(phoneNumber = "09397799139", loginIsSuccessful = {
                        Toast.makeText(this, "login is successful", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        )

        if (intent != null)
            handleIntent()

        binding.inquiryBtn2.init("لاگین", btnOnClick = {


//            PishkhanSDK.login(
//                "09397799139",
//                binding.enterOtpCodeLayout.getText(),
//                confirmOtpIsSuccessful = {
//                    Toast.makeText(PishkhanUser.context, "confirmmmm", Toast.LENGTH_LONG)
//                        .show()
//                })

            PishkhanSDK.onInquiryButtonClicked(
                product = ProductItemDetail.InquiryPlateNumberHistory,
                inputModel = CarPlateNumberHistory.Input(
                    MobileNumber = "09203546839",
                    NationalCode = "0013891480",
                    OTPCode = "",
                    PurchaseKey = null,
                    PlateNumber = "34-ل-644-20"
                )
            ) {

            }

        })

        binding.inquiryBtn.init("استعلام", btnOnClick = {

            PishkhanSDK.onInquiryButtonClicked(
                product = ProductItemDetail.InquiryVehicleAuthenticityByBarCode,
                inputModel = VehicleAuthenticity.Input(
                    IdentifierType = "Barcode",
                    NationalCodeOwner = "2740651236",
                    NationalCodeBuyer = "0021734631",
                    OTPCode = null,
                    OTPReferenceNumber = "",
                    Identifier = "145679348",
                    PurchaseKey = null
                ),
                handleResultCallback = {
                    Log.d("auth", "onCreate: success")
                }
            )
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