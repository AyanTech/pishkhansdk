package ir.ayantech.pishkhansdk.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkFragmentPaymentChannelsBinding
import ir.ayantech.pishkhansdk.helper.PaymentHelper
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.helper.payment.channels.PaymentChannels
import ir.ayantech.pishkhansdk.model.api.AddUserData
import ir.ayantech.pishkhansdk.model.api.PaymentSummary
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.pishkhansdk.model.app_logic.PaymentData
import ir.ayantech.pishkhansdk.model.component_data_model.PishkhansdkExtraInfoComponentDataModel
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.ui.adapter.PishkhansdkPaymentChannelsAdapter
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.components.performClick
import ir.ayantech.pishkhansdk.ui.components.setText
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.StringCallBack
import ir.ayantech.whygoogle.helper.formatAmount
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.safeGet
import ir.ayantech.whygoogle.helper.toJsonString
import ir.ayantech.whygoogle.helper.verticalSetup

abstract class PaymentChannelsFragment: AyanFragment<PishkhansdkFragmentPaymentChannelsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkFragmentPaymentChannelsBinding
        get() = PishkhansdkFragmentPaymentChannelsBinding::inflate

    open val servicesPishkhanAyanApi: AyanApi
        get() = PishkhanSDK.serviceApi

    enum class PaymentChannelSource {
        INVOICE_PAYMENT,
        BILLS_PAYMENT
    }

    abstract val paymentChannelSource: PaymentChannelSource

    abstract val dataForSave: Any

    open var invoicePurchaseKey: String = ""

    open val customWalletFragment: WalletFragment? = null
    open val customCNPGFragment: CNPGFragment? = null

    open val extraInfoComponentDataModel: PishkhansdkExtraInfoComponentDataModel?
        get() = PaymentHelper.extraInfoComponentDataModelForPayViaCNPG

    var paymentChannels: List<PaymentChannel> = listOf()
        set(value) {
            field = value
            getPaymentChannelsSummary()
        }

    open var selectedPaymentChannel: PaymentChannel? = null

    var userBillsPaymentSummary: List<PaymentSummary> = listOf()
        set(value) {
            field = value
            initPaymentChannelsRv()
        }

    open var defaultSelectedPosition = 0

    open var shouldCheckWalletPaymentChannelBalance: Boolean = false

    abstract fun getPaymentChannels()

    abstract fun getPaymentChannelsSummary()

    override fun onCreate() {
        super.onCreate()

        initViews()
    }

    open fun initViews() {
        getPaymentChannels()
        initPayButton()
    }

    open fun setDefaultPaymentChannel() {
        paymentChannels.filter { it.Active }.safeGet(defaultSelectedPosition)?.let {
            it.Selected = true
            this@PaymentChannelsFragment.selectedPaymentChannel = it
            updateAmounts(it)
        }
    }

    open fun addWalletDataToWalletPaymentChannel() {
        paymentChannels.firstOrNull { it.Type.Name == PaymentChannels.Wallet.name }
            ?.let { paymentChannel ->
                userBillsPaymentSummary.firstOrNull { it.ChannelType == PaymentChannels.Wallet.name }
                    ?.let { paymentSummary ->
                        paymentChannel.isWalletBalanceSufficient = paymentSummary.HasBalance
                        paymentChannel.walletBalance = paymentSummary.Cash
                    }
            }
    }

    open fun initPaymentChannelsRv() {
        setDefaultPaymentChannel()
        addWalletDataToWalletPaymentChannel()
        accessViews {
            paymentChannelsRv.apply {
                verticalSetup()
                adapter = PishkhansdkPaymentChannelsAdapter(items = paymentChannels) { item, viewId, position ->
                    item?.let {
                        onSelectedPaymentChannelChanged(it)
                    }
                }
            }
        }
        checkWalletPaymentChannelBalanceForPerformPayButton()
    }

    open fun onSelectedPaymentChannelChanged(paymentChannel: PaymentChannel) {
        this@PaymentChannelsFragment.selectedPaymentChannel = paymentChannel
        updatePaymentChannels(paymentChannel)
        updateAmounts(paymentChannel)
        onPaymentChannelSelected(paymentChannel)
    }

    open fun checkWalletPaymentChannelBalanceForPerformPayButton() {
        if (shouldCheckWalletPaymentChannelBalance) {
            userBillsPaymentSummary.firstOrNull { it.ChannelType == PaymentChannels.Wallet.name }
                ?.let { paymentSummary ->
                    paymentChannels.firstOrNull { it.Type.Name == PaymentChannels.Wallet.name }
                        ?.let { paymentChannel ->
                            onSelectedPaymentChannelChanged(paymentChannel)
                            if (paymentSummary.HasBalance) {
                                binding.payButtonComponent.performClick()
                            }
                        }
                }
            shouldCheckWalletPaymentChannelBalance = false
        }
    }

    open fun onPaymentChannelSelected(paymentChannel: PaymentChannel) {}

    open fun updateAmounts(paymentChannel: PaymentChannel) {
        userBillsPaymentSummary.firstOrNull { it.ChannelType == paymentChannel.Type.Name }?.let { paymentSummary ->
            updateTotalAmount(paymentSummary.TotalAmount)
            updateFeeAmount(paymentSummary.Fee)
            updateServiceAmount(paymentSummary.Amount)
        }
    }

    open fun updateTotalAmount(totalAmount: Long) {
        binding.payButtonComponent.setText(getString(R.string.pay_amount, totalAmount.formatAmount()))
    }

    open fun updateFeeAmount(feeAmount: Long) {
        binding.feeTv.text = feeAmount.formatAmount()
    }

    open fun updateServiceAmount(serviceAmount: Long) {
        binding.amountTv.text = serviceAmount.formatAmount()
    }

    open fun updatePaymentChannels(paymentChannel: PaymentChannel) {
        (binding.paymentChannelsRv.adapter as? PishkhansdkPaymentChannelsAdapter)?.setSelectedItem(paymentChannel)
    }

    abstract fun getPaymentData(callback: (paymentData: PaymentData) -> Unit)

    abstract fun callWalletPayment(paymentKey: String)

    open fun openWalletForCharge() {
        saveUserData { dataId ->
            start(
                customWalletFragment ?: WalletFragment(
                    source = WalletFragment.Source.Service,
                    neededCashForChargeWallet = userBillsPaymentSummary.firstOrNull { it.ChannelType == PaymentChannels.Wallet.name }?.NeededCashAmount ?: 0,
                    callbackDataModel = CallbackDataModel(
                        sourcePage = paymentChannelSource.name,
                        purchaseKey = invoicePurchaseKey,
                        selectedGateway = if (selectedPaymentChannel?.Type?.Name == PaymentChannels.OnlinePayment.name) null else selectedPaymentChannel?.Type?.Name,
                        serviceName = PishkhanSDK.serviceName,
                        useCredit = "false",
                        voucherCode = null,
                        dataId = dataId
                    ),
                    walletChargeSuccessfulViaCNPG = {
                        pop()
                        pop()
                        shouldCheckWalletPaymentChannelBalance = true
                        initViews()
                    }
                )
            )
        }
    }

    abstract fun onWalletChargedViaCNPG(savedData: String)

    open fun initPayButton() {
        accessViews {
            payButtonComponent.init(
                btnText = getString(R.string.pay)
            ) {
                getPaymentData { paymentData ->
                    onPaymentButtonClicked(paymentData)
                }
            }
        }
    }

    open fun onPaymentButtonClicked(paymentData: PaymentData) {
        when (selectedPaymentChannel?.Type?.Name) {
            PaymentChannels.Wallet.name -> {
                if (selectedPaymentChannel?.isWalletBalanceSufficient == true) {
                    callWalletPayment(paymentData.PaymentKey)
                } else {
                    openWalletForCharge()
                }
            }

            PaymentChannels.OnlinePayment.name -> {
                paymentData.RedirectLink?.openUrl(requireActivity())
            }

            PaymentChannels.CNPG.name -> {
                start(customCNPGFragment ?: CNPGFragment(
                    paymentKey = paymentData.PaymentKey,
                    onCNPGPaymentSucceeded = {
                        pop()
                        pop()
                        onCNPGPaymentSucceeded()
                    },
                    extraInfoComponentDataModel = extraInfoComponentDataModel
                ))
            }
        }
    }

    abstract val onCNPGPaymentSucceeded: SimpleCallBack

    open fun saveUserData(callback: StringCallBack) {
        servicesPishkhanAyanApi.call<AddUserData.Output>(
            endPoint = EndPoints.ADD_USER_DATA,
            input = AddUserData.Input(
                Key = null,
                Value = dataForSave.toJsonString()
            )
        ) {
            success {
                it?.DataID?.let { dataId ->
                    callback(dataId)
                }
            }
        }
    }
}