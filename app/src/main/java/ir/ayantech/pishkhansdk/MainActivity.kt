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
import ir.ayantech.pishkhansdk.model.api.TransferTaxCarV2
import ir.ayantech.pishkhansdk.model.api.VehicleAuthenticity
import ir.ayantech.pishkhansdk.model.app_logic.ProductItemDetail
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.fragments.WalletFragment
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import kotlin.jvm.java


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
     /*    PishkhanSDK.handleUserSession(
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
        )*/
        if (intent != null)
            handleIntent()



        PishkhanUser.token ="4F5526F8FC4E440CA313FAD155703463"
/*        PishkhanSDK.onInquiryButtonClicked(
            showPaymentChannelsFragment = false,
            inputModel = TransferTaxCarV2.Input(
                PlateNumber = "98-ی-144-50",
                NationalCode = "2740185030",
                PurchaseKey = null,
                DateOfBirth = "1404/12/3"
            ),
            failureCallBack = {},
            product = ProductItemDetail.InquiryTransferTaxCarV2,
            handleResultCallback = { response ->

            })*/

        start(WalletFragment())
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