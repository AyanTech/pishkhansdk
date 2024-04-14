package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.DialogOtpBinding
import ir.ayantech.pishkhansdk.model.app_logic.OTP
import ir.ayantech.pishkhansdk.ui.components.changeEnable
import ir.ayantech.pishkhansdk.ui.components.getText
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.components.setAfterTextChangesListener
import ir.ayantech.pishkhansdk.ui.components.setError
import ir.ayantech.pishkhansdk.ui.components.setText
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.repeatTryingUntil
import ir.ayantech.whygoogle.helper.trying
import kotlin.math.roundToLong

class OtpBottomSheetDialog(
    context: Context,
    private val otp: OTP?,
    private val callback: (String?) -> Unit
) : BaseBottomSheet<DialogOtpBinding>(context) {

    override val binder: (LayoutInflater) -> DialogOtpBinding
        get() = DialogOtpBinding::inflate

    override val title: String
        get() = "تایید رمز عبور یک بار مصرف"

    init {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTimer()
        initViews()
        setupActions()
    }

    private fun setupActions() {
        binding.apply {
/*
            enterOtpCodeLayout.retryTv.setOnClickListener {
                dismiss()
                callback(null)
            }
*/

        }
    }

    fun setError(failureMessage: String) {
        binding.apply {
            enterOtpCodeLayout.textInputEditText.setError(failureMessage)
        }
    }

    fun setAutoFillCode(code: String) {
        binding.apply {
            enterOtpCodeLayout.setText(code)
        }
    }

    private fun initViews() {
        binding.apply {
            descriptionTv.changeVisibility(otp?.Message.isNotNull())
            secondDescriptionLayout.changeVisibility(otp?.Message?.split(";")?.size == 2)
            otp?.Message?.let {
                descriptionTv.text = it.split(";")[0]
                if (it.split(";").size > 1) {
                    secondDescriptionTitleTv.text = "شماره تماس ثبت شده: "
                    secondDescriptionValueTv.text = it.split(";")[1]
                }

                secondDescriptionLayout.changeVisibility(it.split(";").size > 1 && it.split(";")[1].isNotEmpty())
            }

            enterOtpCodeLayout.init(
                context = context,
                hint = context.getString(R.string.activation_code),
                inputType = InputType.TYPE_CLASS_NUMBER,
                maxLength = otp?.Length?.toInt()!!,
            )

            enterOtpCodeLayout.setAfterTextChangesListener {
                confirmCodeBtn.changeEnable(it.length >= otp.Length.toInt())
            }

            confirmCodeBtn.init(btnText = "تایید", isEnable = false) {
                when {
                    enterOtpCodeLayout.getText().length < otp.Length -> {
                        enterOtpCodeLayout.setError("لطفا کد ارسال شده را وارد کنید.")
                    }

                    else -> {
                        dismiss()
                        callback(enterOtpCodeLayout.getText())
                    }
                }
            }
        }
    }

  private fun startTimer() {
      repeatTryingUntil({ otp?.Timer != null }) {
          object : CountDownTimer((otp?.Timer!!).roundToLong(), 1000) {
              override fun onFinish() {
                  trying {
                      accessViews {
                          retryTv.isEnabled = true
                          retryTv.text = getString(R.string.resend)
                      }
                  }
              }

              override fun onTick(p0: Long) {
                  try {
                      accessViews {
                          retryTv.isEnabled = false
                          retryTv.text = "${p0 / 1000} ثانیه تا دریافت مجدد کد تایید "
                      }
                  } catch (e: Exception) {

                  }
              }
          }.start()
      }
    }
}