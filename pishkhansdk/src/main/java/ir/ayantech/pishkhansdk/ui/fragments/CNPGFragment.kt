package ir.ayantech.pishkhansdk.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.SimpleCallback
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkFragmentCnpgBinding
import ir.ayantech.pishkhansdk.helper.PishkhanSDK
import ir.ayantech.pishkhansdk.helper.extensions.changeVisibility
import ir.ayantech.pishkhansdk.helper.extensions.extractOtp
import ir.ayantech.pishkhansdk.helper.utils.SmsOtpReader
import ir.ayantech.pishkhansdk.helper.utils.smsOtpReader
import ir.ayantech.pishkhansdk.model.api.CnpgGetCardList
import ir.ayantech.pishkhansdk.model.api.CnpgPayment
import ir.ayantech.pishkhansdk.model.api.CnpgRemoveCard
import ir.ayantech.pishkhansdk.model.api.CnpgRequestOtp
import ir.ayantech.pishkhansdk.model.api.CnpgRequestParameters
import ir.ayantech.pishkhansdk.model.api.ShaparakBankListService
import ir.ayantech.pishkhansdk.model.api.UserPublicKey
import ir.ayantech.pishkhansdk.model.api.encryptData
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.ui.bottom_sheet.PishkhansdkCNPGFailedPaymentBottomSheet
import ir.ayantech.pishkhansdk.ui.bottom_sheet.PishkhansdkCNPGSuccessfulPaymentBottomSheet
import ir.ayantech.pishkhansdk.ui.bottom_sheet.PishkhansdkSelectBankCardNumberBottomSheet
import ir.ayantech.pishkhansdk.ui.components.PishkhansdkExtraInfoComponentDataModel
import ir.ayantech.pishkhansdk.ui.components.changeEnable
import ir.ayantech.pishkhansdk.ui.components.changeRemainingTimeVisibility
import ir.ayantech.pishkhansdk.ui.components.clearDefaultDate
import ir.ayantech.pishkhansdk.ui.components.getPasswordText
import ir.ayantech.pishkhansdk.ui.components.getText
import ir.ayantech.pishkhansdk.ui.components.getYearAndMonthText
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.components.initBankCardDateComponent
import ir.ayantech.pishkhansdk.ui.components.initBankCardNumberComponent
import ir.ayantech.pishkhansdk.ui.components.initBankCardPassword
import ir.ayantech.pishkhansdk.ui.components.initExtraInfoComponent
import ir.ayantech.pishkhansdk.ui.components.setDefaultDate
import ir.ayantech.pishkhansdk.ui.components.setInputType
import ir.ayantech.pishkhansdk.ui.components.setRemainingTime
import ir.ayantech.pishkhansdk.ui.components.setText
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.isNull
import ir.ayantech.whygoogle.helper.trying
import java.util.UUID

open class CNPGFragment(): AyanFragment<PishkhansdkFragmentCnpgBinding>() {

    open val corePishkhan24AyanApi: AyanApi
        get() = PishkhanSDK.coreApi

    constructor(
        paymentKey: String,
        onCNPGPaymentSucceeded: SimpleCallBack,
        extraInfoComponentDataModel: PishkhansdkExtraInfoComponentDataModel? = null
    ): this() {
        this.paymentKey = paymentKey
        this.onCNPGPaymentSucceeded = onCNPGPaymentSucceeded
        this.extraInfoComponentDataModel = extraInfoComponentDataModel
    }
    var paymentKey: String? = null
    var onCNPGPaymentSucceeded: SimpleCallBack = {}

    var extraInfoComponentDataModel: PishkhansdkExtraInfoComponentDataModel? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkFragmentCnpgBinding
        get() = PishkhansdkFragmentCnpgBinding::inflate

    private var selectedBankCard: CnpgGetCardList.BankCard? = null

    override val defaultBackground: Int
        get() = R.color.pishkhansdk_cnpg_fragment_default_background

    lateinit var publicKey: String

    private var selectBankCardNumberBottomSheet: PishkhansdkSelectBankCardNumberBottomSheet? = null
    private var bankList: List<ShaparakBankListService.ShaparakBank>? = null
    private var userCardList: List<CnpgGetCardList.BankCard>? = null
    open var regex: String? = null
    open val requestOtpTimeout = 120000L
    open var timer: CountDownTimer? = null

    lateinit var smsReader: SmsOtpReader

    override fun onAttach(context: Context) {
        super.onAttach(context)

        smsReader = smsOtpReader(
                callbacks = object : SmsOtpReader.Callbacks {
                    override fun onOtp(code: String) {
                        // Example: fill UI and continue
                        binding.bankCardPasswordComponent.passwordInputComponent.setText(code.extractOtp(regex ?: ""))
                        binding.payButtonComponent.changeEnable(enable = true)
                        hideKeyboard()
                    }
                    override fun onTimeout() {
                        // Show "Resend code" UI, etc.
                    }
                    override fun onError(t: Throwable) {
                        // Optional logging/UI
                        t.printStackTrace()
                    }
                },
                extractor = { message ->
                    // Use your own regex/extractor if you like:
                    // Example: find the first number group in a line that matches your own passRegex idea
                    Regex(regex ?: "\\b\\d{6}\\b").find(message)?.value
                }
            )

    }

    override fun onCreate() {
        super.onCreate()

        getPublicKey()

    }

    private fun getPublicKey() {
        corePishkhan24AyanApi.call<UserPublicKey.Output>(
            endPoint = EndPoints.USER_PUBLIC_KEY,
        ) {
            success { userPublicKeyOutput ->
                userPublicKeyOutput?.PublicKey?.let { publicKey ->
                    this@CNPGFragment.publicKey = publicKey
                    initViews()
                    getBankList {
                        getUserCardList {
                            attachBankDataToUserCardList()
                            selectBankCardNumberBottomSheet?.bankCards = userCardList ?: listOf()
                            initSelectBankCardNumberBottomSheet()
                        }
                    }
                }
            }
        }
    }

    open fun attachBankDataToUserCardList() {
        userCardList?.forEach { card ->
            bankList?.firstOrNull { bank -> bank.ID == card.BankID }?.let { bank ->
                card.BankName = bank.ShowName
                card.Icon = bank.Icon
            }
        }
    }

    open fun initSelectBankCardNumberBottomSheet() {
        selectBankCardNumberBottomSheet = PishkhansdkSelectBankCardNumberBottomSheet(
            context = requireActivity(),
            bankCards = userCardList ?: listOf(),
            onRemoveCardConfirmed = {
                removeUserBankCard(it)
            }
        ) { bankCard ->
            selectedBankCard = bankCard
            binding.bankCardNumberComponent.setText(text = bankCard.MaskedPan)
            if (bankCard.CardID.isNotEmpty()) {
                binding.bankCardDateComponent.setDefaultDate()
            } else {
                binding.bankCardDateComponent.clearDefaultDate()
            }
            timer?.cancel()
            binding.bankCardPasswordComponent.changeRemainingTimeVisibility(show = false)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    open fun initViews() {
        accessViews {

            initExtraInfoComponent()

            initSelectBankCardNumberBottomSheet()

            bankCardNumberComponent.initBankCardNumberComponent(
                hint = getString(R.string.bank_card_number),
            ) {
                selectBankCardNumberBottomSheet?.show()
            }

            bankCardDateComponent.initBankCardDateComponent(
                context = requireActivity()
            )

            bankCardCvv2Component.init(
                context = requireActivity(),
                hint = getString(R.string.cvv2),
                maxLength = 4,
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD,
                endDrawable = R.drawable.pishkhansdk_ic_visibility,
                onEndIconTouchListener = View.OnTouchListener { _, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> bankCardCvv2Component.setInputType(InputType.TYPE_CLASS_NUMBER)
                        MotionEvent.ACTION_UP -> bankCardCvv2Component.setInputType(
                            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                        )

                        MotionEvent.ACTION_CANCEL -> bankCardCvv2Component.setInputType(
                            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                        )
                    }
                    true
                }
            )

            bankCardPasswordComponent.initBankCardPassword(
                context = requireActivity(),
                afterTextChangedCallback = {
                    payButtonComponent.changeEnable(enable = it.length >= 4)
                },
                onGetOtpCodeButtonClicked = {
                    checkInputs {
                        sendOtpCode()
                    }
                }
            )

            payButtonComponent.init(
                btnText = getString(R.string.pay),
                isEnable = false
            ) {
                payViaCNPG()
            }
        }
    }

    open fun initExtraInfoComponent() {
        accessViews {
            extraInfoComponent.changeVisibility(show = extraInfoComponentDataModel.isNotNull())
            extraInfoComponentDataModel?.let {
                extraInfoComponent.initExtraInfoComponent(data = it)
            }
        }
    }

    open fun payViaCNPG() {
        checkInputs {
            corePishkhan24AyanApi.call<CnpgPayment.Output>(
                endPoint = EndPoints.CNPG_PAYMENT,
                input = CnpgPayment.Input(
                    PaymentKey = paymentKey ?: "",
                    EncryptData = getEncryptedData()
                )
            ) {
                useCommonFailureCallback = false
                success {
                    smsReader.stop()
                    timer?.cancel()
                    timer?.onFinish()
                    PishkhansdkCNPGSuccessfulPaymentBottomSheet(
                        context = requireActivity(),
                        onReturnButtonClicked = onCNPGPaymentSucceeded
                    ).show()
                }
                failure {
                    PishkhansdkCNPGFailedPaymentBottomSheet(
                        context = requireActivity(),
                        failureMessage = it.failureMessage
                    ).show()
                }
            }
        }
    }

    open fun getEncryptedData() = CnpgRequestParameters(
        Pan = if (selectedBankCard?.CardID?.isEmpty() == true) selectedBankCard?.MaskedPan ?: "" else selectedBankCard?.CardID ?: "",
        ExpireDate = binding.bankCardDateComponent.getYearAndMonthText(),
        Cvv2 = binding.bankCardCvv2Component.getText(),
        Pin = binding.bankCardPasswordComponent.getPasswordText(),
        SaveCard = binding.saveCardDataCb.isChecked,
        TraceNumber = UUID.randomUUID().toString()
    ).encryptData(publicKey)

    open fun checkInputs(callback: SimpleCallback) {
        val errorMessage = when {
            selectedBankCard.isNull() -> getString(R.string.enter_or_choose_your_bank_card_number)
            binding.bankCardDateComponent.getYearAndMonthText().isEmpty() -> getString(R.string.enter_bank_card_date)
            binding.bankCardCvv2Component.getText().isEmpty() -> getString(R.string.enter_bank_card_cvv2)
            else -> null
        }
        if (errorMessage == null) {
            callback()
        } else {
            Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    open fun sendOtpCode() {
        corePishkhan24AyanApi.call<Void>(
            endPoint = EndPoints.CNPG_REQUEST_OTP,
            input = CnpgRequestOtp.Input(
                PaymentKey = paymentKey ?: "",
                EncryptData = getEncryptedData()
            )
        ) {
            success {
                bankList?.firstOrNull { bank ->
                    bank.Code.firstOrNull {
                        selectedBankCard?.MaskedPan?.startsWith(it) == true
                    } != null
                }?.let { bank ->
                    this@CNPGFragment.regex = bank.PurchaseOtpCodeRegex
                    smsReader.start()
                }
                binding.bankCardPasswordComponent.changeRemainingTimeVisibility(true)
                startTimer()
            }
        }
    }

    open fun startTimer() {
        timer = object : CountDownTimer(requestOtpTimeout, 1000) {
            override fun onFinish() {
                trying {
                    binding.bankCardPasswordComponent.changeRemainingTimeVisibility(false)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                try {
                    binding.bankCardPasswordComponent.setRemainingTime(context = requireActivity(), (millisUntilFinished / 1000))
                } catch (e: Exception) {
                    timer?.cancel()
                }
            }
        }.also { it.start() }
    }

    open fun getBankList(callback: SimpleCallback? = null) {
        corePishkhan24AyanApi.call<ShaparakBankListService.Output>(
            endPoint = EndPoints.SHAPARAK_BANK_LIST_SERVICE
        ) {
            success { bankList ->
                this@CNPGFragment.bankList = bankList?.AllList
                callback?.invoke()
            }
        }
    }

    open fun getUserCardList(callback: SimpleCallback? = null) {
        corePishkhan24AyanApi.call<CnpgGetCardList.Output>(
            endPoint = EndPoints.CNPG_GET_CARD_LIST,
            input = CnpgGetCardList.Input(PaymentKey = paymentKey ?: "")
        ) {
            success { userCardList ->
                this@CNPGFragment.userCardList = userCardList?.CardList
                callback?.invoke()
            }
        }
    }

    open fun removeUserBankCard(bankCard: CnpgGetCardList.BankCard) {
        corePishkhan24AyanApi.call<Void>(
            endPoint = EndPoints.CNPG_REMOVE_CARD,
            input = CnpgRemoveCard.Input(
                PaymentKey = paymentKey ?: "",
                CardID = bankCard.CardID
            )
        ) {
            success {
                getUserCardList {
                    attachBankDataToUserCardList()
                    selectBankCardNumberBottomSheet?.updateBankCards(bankCard)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        smsReader.stop()
    }

}