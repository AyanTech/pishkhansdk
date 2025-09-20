package ir.ayantech.pishkhansdk.helper.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

typealias OtpExtractor = (String) -> String?

interface SmsOtpReader {
    /**
     * Start listening for a single consent SMS.
     * @param senderPhone Pass a specific sender phone (E.164) to filter, or null to accept any.
     */
    fun start(senderPhone: String? = null)

    /** Stop listening and unregister the receiver (safe to call multiple times). */
    fun stop()

    /** Replace the OTP extractor at runtime (e.g. when flow changes). */
    var extractor: OtpExtractor

    interface Callbacks {
        /** Called when an OTP code was extracted. */
        fun onOtp(code: String)

        /** Called with full SMS text if extractor returns null (optional fallback). */
        fun onFullMessage(message: String) {}

        /** Called if the Consent API times out (~5 minutes). */
        fun onTimeout() {}

        /** Called on any unexpected error. */
        fun onError(t: Throwable) {}
    }
}

/** Factory to create a Fragment-tied implementation. */
fun Fragment.smsOtpReader(
    callbacks: SmsOtpReader.Callbacks,
    extractor: OtpExtractor = { Regex("\\b\\d{4,8}\\b").find(it)?.value } // default: 4–8 digits
): SmsOtpReader = SmsUserConsentReaderImpl(this, callbacks, extractor)

internal class SmsUserConsentReaderImpl(
    private val fragment: Fragment,
    private val callbacks: SmsOtpReader.Callbacks,
    initialExtractor: OtpExtractor
) : SmsOtpReader, DefaultLifecycleObserver {

    override var extractor: OtpExtractor = initialExtractor

    private var registered = false

    private val consentLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val msg = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    if (msg != null) {
                        val code = runCatching { extractor(msg) }.getOrNull()
                        if (code != null) callbacks.onOtp(code) else callbacks.onFullMessage(msg)
                    }
                }
            } catch (t: Throwable) {
                callbacks.onError(t)
            }
        }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION != intent.action) return
            val extras = intent.extras ?: return
            val status = extras.get(SmsRetriever.EXTRA_STATUS) as? Status ?: return
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    @Suppress("DEPRECATION")
                    val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    if (consentIntent != null) {
                        runCatching { consentLauncher.launch(consentIntent) }
                            .onFailure { callbacks.onError(it) }
                    }
                }
                CommonStatusCodes.TIMEOUT -> callbacks.onTimeout()
            }
        }
    }

    init {
        // Ensure we always clean up if the Fragment gets destroyed without explicit stop()
        fragment.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stop()
    }

    override fun start(senderPhone: String?) {
        // 1) Start the consent flow (must be called before the receiver fires)
        runCatching {
            SmsRetriever.getClient(fragment.requireActivity()).startSmsUserConsent(senderPhone)
        }.onFailure { callbacks.onError(it) }

        // 2) Register receiver once
        if (!registered) {
            val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            registered = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.registerReceiver(
                        fragment.requireContext(),
                        receiver,
                        filter,
                        ContextCompat.RECEIVER_EXPORTED
                    )
                } else {
                    ContextCompat.registerReceiver(
                        fragment.requireContext(),
                        receiver,
                        filter,
                        ContextCompat.RECEIVER_NOT_EXPORTED
                    )
                    receiver // non-null placeholder
                } != null
            } catch (t: Throwable) {
                callbacks.onError(t)
                false
            }
        }
    }

    override fun stop() {
        if (!registered) return
        registered = try {
            fragment.requireContext().unregisterReceiver(receiver)
            false
        } catch (_: IllegalArgumentException) {
            // already unregistered / context gone
            false
        }
    }
}
