package ir.ayantech.pishkhansdk.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkFragmentWalletBinding
import ir.ayantech.pishkhansdk.helper.PaymentHelper
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.helper.extensions.loadFromString
import ir.ayantech.pishkhansdk.helper.payment.channels.PaymentChannels
import ir.ayantech.pishkhansdk.model.api.UserWalletBalance
import ir.ayantech.pishkhansdk.model.api.UserWalletCharge
import ir.ayantech.pishkhansdk.model.api.UserWalletChargeChannels
import ir.ayantech.pishkhansdk.model.api.UserWallets
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.ui.adapter.PishkhansdkSuggestionAmountsAdapter
import ir.ayantech.pishkhansdk.ui.adapter.PishkhansdkWalletPaymentChannelsAdapter
import ir.ayantech.pishkhansdk.ui.bottom_sheet.PishkhanSdkWalletTermsAndConditionsBottomSheet
import ir.ayantech.pishkhansdk.ui.components.PishkhansdkExtraInfoComponentDataModel
import ir.ayantech.pishkhansdk.ui.components.getAmount
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.components.initInputAmountCounterComponent
import ir.ayantech.pishkhansdk.ui.components.setText
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.delayedTransition
import ir.ayantech.whygoogle.helper.formatAmount
import ir.ayantech.whygoogle.helper.isVisible
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.rtlSetup

open class WalletFragment(): AyanFragment<PishkhansdkFragmentWalletBinding>() {

    open val corePishkhan24AyanApi: AyanApi
        get() = PishkhanSDK.coreApi

    constructor(
        source: Source,
        neededCashForChargeWallet: Long,
        callbackDataModel: CallbackDataModel,
        walletChargeSuccessfulViaCNPG: SimpleCallBack
    ): this() {
        this.source = source
        this.neededCashForChargeWallet = neededCashForChargeWallet
        this.callbackDataModel = callbackDataModel
        this.walletChargeSuccessfulViaCNPG = walletChargeSuccessfulViaCNPG
    }

    open var source: Source = Source.DirectCharge
    open var callbackDataModel: CallbackDataModel? = null
    open var neededCashForChargeWallet: Long? = null

    open var walletChargeSuccessfulViaCNPG: SimpleCallBack = {}

    enum class Source {
        Service,
        DirectCharge
    }

    open val extraInfoComponentDataModel: PishkhansdkExtraInfoComponentDataModel?
        get() = PaymentHelper.extraInfoComponentDataModelForPayViaCNPG

    private var selectedPaymentChannel: PaymentChannel? = null

    override val defaultBackground: Int
        get() = R.color.pishkhansdk_wallet_fragment_default_background

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkFragmentWalletBinding
        get() = PishkhansdkFragmentWalletBinding::inflate

    open var walletTypeName: String = ""

    override fun onCreate() {
        super.onCreate()

        initViews()
    }

    open fun initViews() {
        initTopSection()
    }

    open fun initTopSection() {
        getUserWallets()
    }

    open fun initSuggestionAmounts(amounts: List<Long>) {
        accessViews {
            amountsRv.apply {
                rtlSetup(3)
                adapter = PishkhansdkSuggestionAmountsAdapter(
                    items = amounts
                ) { selectedAmount, viewId, position ->
                    selectedAmount?.let {
                        inputAmountCounterComponent.setText(selectedAmount.formatAmount(""))
                    }
                }
            }
        }
    }

    open fun initAmountCounter(chargeSettings: UserWallets.ChargeSettings) {
        accessViews {
            inputAmountCounterComponent.initInputAmountCounterComponent(
                context = requireActivity(),
                increaseAndDecreaseValue = chargeSettings.Interval.toInt(),
                minimumValue = chargeSettings.Minimum.toInt(),
                defaultValue = neededCashForChargeWallet?.toInt() ?: chargeSettings.Minimum.toInt()
            ) {

            }
        }
    }

    open fun initBottomSection() {
        getUserWalletChargeChannels()
        initPaymentButton()
    }

    open fun getUserWalletChargeChannels() {
        corePishkhan24AyanApi.call<UserWalletChargeChannels.Output>(
            endPoint = EndPoints.USER_WALLET_CHARGE_CHANNELS,
            input = UserWalletChargeChannels.Input(
                Wallet = walletTypeName
            )
        ) {
            success {
                it?.PaymentChannels?.let { paymentChannels -> initPaymentChannels(paymentChannels) }
            }
        }
    }

    open fun setDefaultPaymentChannel(paymentChannels: List<PaymentChannel>) {
        paymentChannels.firstOrNull()?.let { defaultPaymentChannel ->
            defaultPaymentChannel.Selected = true
            defaultPaymentChannel.Gateways?.firstOrNull()?.let { firstGateway ->
                accessViews {
                    paymentMethodShowNameTv.text = firstGateway.Type.ShowName

                    if (paymentChannels.size > 1) {
                        paymentMethodIconIv.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireActivity(),
                                R.drawable.pishkhansdk_check_circle
                            )
                        )
                    } else {
                        firstGateway.Icon?.let { paymentMethodIconIv.loadFromString(it) } ?: run {
                            paymentMethodIconIv.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireActivity(),
                                    R.drawable.pishkhansdk_check_circle
                                )
                            )
                        }
                    }
                }
            }
            selectedPaymentChannel = defaultPaymentChannel
        }
    }

    open fun initPaymentChannels(paymentChannels: List<PaymentChannel>) {
        setDefaultPaymentChannel(paymentChannels)
        accessViews {
            paymentMethodsRv.apply {
                rtlSetup(2)
                adapter = PishkhansdkWalletPaymentChannelsAdapter(
                    items = paymentChannels
                ) { selectedPaymentChannel, viewId, position ->
                    selectedPaymentChannel?.let {
                        this@WalletFragment.selectedPaymentChannel = selectedPaymentChannel
                        paymentMethodShowNameTv.text = selectedPaymentChannel.Gateways?.firstOrNull()?.Type?.ShowName
                        (paymentMethodsRv.adapter as? PishkhansdkWalletPaymentChannelsAdapter)?.setSelectedItem(selectedPaymentChannel)
                    }
                }
            }
            dropDownIconIv.changeVisibility(show = paymentChannels.size > 1)
            dropDownIconIv.setOnClickListener {
                paymentSectionRl.delayedTransition()
                paymentMethodsRv.changeVisibility(show = !paymentMethodsRv.isVisible())
                if (paymentMethodsRv.isVisible()) {
                    dropDownIconIv.rotation = 0f
                } else {
                    dropDownIconIv.rotation = 180f
                }
            }
        }
    }

    open fun initPaymentButton() {
        accessViews {
            paymentButtonComponent.init(
                btnText = getString(R.string.pay)
            ) {
                selectedPaymentChannel?.Gateways?.firstOrNull()?.let { gateway ->
                    corePishkhan24AyanApi.call<UserWalletCharge.Output>(
                        endPoint = EndPoints.USER_WALLET_CHARGE,
                        input = UserWalletCharge.Input(
                            Amount = inputAmountCounterComponent.getAmount(),
                            Callback = createCallback(),
                            Gateway = gateway.Type.Name,
                            Wallet = walletTypeName
                        )
                    ) {
                        success { userWalletChargeOutput ->
                            when(selectedPaymentChannel?.Type?.Name) {
                                PaymentChannels.OnlinePayment.name -> {
                                    userWalletChargeOutput?.RedirectLink?.openUrl(requireActivity())
                                }

                                PaymentChannels.CNPG.name -> {
                                    start(CNPGFragment(
                                        paymentKey = userWalletChargeOutput?.PaymentKey ?: "",
                                        onCNPGPaymentSucceeded = if (source == Source.DirectCharge) {
                                            onWalletDirectChargedViaCNPG
                                        } else {
                                            walletChargeSuccessfulViaCNPG
                                        },
                                        extraInfoComponentDataModel = if (source == Source.DirectCharge) null else extraInfoComponentDataModel
                                    ))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    open val onWalletDirectChargedViaCNPG: SimpleCallBack = {
        pop()
        initViews()
    }

    open fun createCallback() =
        if (source == Source.DirectCharge)
            CallbackDataModel(
                sourcePage = source.name,
                selectedGateway = walletTypeName,
                amount = binding.inputAmountCounterComponent.getAmount().toString(),
                purchaseKey = null,
            ).createCallBackLink()
        else
            callbackDataModel?.createCallBackLink() ?: ""

    open fun getUserWallets() {
        corePishkhan24AyanApi.call<UserWallets.Output>(
            endPoint = EndPoints.USER_WALLETS,
        ) {
            success {
                it?.firstOrNull()?.let { wallet ->
                    walletTypeName = wallet.Type.Name
                    getUserWalletBalance()
                    initBottomSection()
                    initTermsAndConditions(wallet.TermsAndConditions)
                    initSuggestionAmounts(wallet.ChargeSettings.SuggestedAmounts)
                    initAmountCounter(wallet.ChargeSettings)
                }
            }
        }
    }

    open fun initTermsAndConditions(termsAndConditions: String?) {
        accessViews {
            termsAndConditionsTv.setOnClickListener {
                PishkhanSdkWalletTermsAndConditionsBottomSheet(
                    context = requireActivity(),
                    termsAndConditions = termsAndConditions
                ).show()
            }
        }
    }

    open fun getUserWalletBalance() {
        corePishkhan24AyanApi.call<UserWalletBalance.Output>(
            endPoint = EndPoints.USER_WALLET_BALANCE,
            input = UserWalletBalance.Input(Wallet = walletTypeName)
        ) {
            success {
                accessViews {
                    walletBalanceTv.text = it?.Cash?.formatAmount()
                }
            }
        }
    }
}