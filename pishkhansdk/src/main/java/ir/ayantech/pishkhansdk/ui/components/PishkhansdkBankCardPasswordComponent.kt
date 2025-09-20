package ir.ayantech.pishkhansdk.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentBankCardPasswordBinding
import ir.ayantech.pishkhansdk.helper.extensions.AfterTextChangedCallback
import ir.ayantech.pishkhansdk.helper.extensions.changeVisibility
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNull

@SuppressLint("ClickableViewAccessibility")
fun PishkhansdkComponentBankCardPasswordBinding.initBankCardPassword(
    context: Context,
    afterTextChangedCallback: AfterTextChangedCallback,
    onGetOtpCodeButtonClicked: SimpleCallBack
) {
    passwordInputComponent.init(
        context = context,
        hint = context.getString(R.string.bank_card_password),
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD,
        endDrawable = R.drawable.pishkhansdk_ic_visibility,
        onEndIconTouchListener = View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> passwordInputComponent.setInputType(InputType.TYPE_CLASS_NUMBER)
                MotionEvent.ACTION_UP -> passwordInputComponent.setInputType(
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                )

                MotionEvent.ACTION_CANCEL -> passwordInputComponent.setInputType(
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                )
            }
            true
        }
    )
    passwordInputComponent.setAfterTextChangesListener(afterTextChangedCallback)

    sendOtpButtonComponent.init(
        context = context,
        btnText = context.getString(R.string.get_otp)
    ) {
        onGetOtpCodeButtonClicked()
    }
}

fun PishkhansdkComponentBankCardPasswordBinding.getPasswordText() = passwordInputComponent.getText()

fun PishkhansdkComponentBankCardPasswordBinding.changeRemainingTimeVisibility(show: Boolean) {
    sendOtpButtonComponent.changeVisibility(show = !show)
    remainingTimeTv.changeVisibility(show = show)
}

fun PishkhansdkComponentBankCardPasswordBinding.setRemainingTime(context: Context, remainingTime: Long) {
    remainingTimeTv.text = context.getString(R.string.remaining_time, remainingTime.toString())
}