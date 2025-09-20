package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.BILLS_PAYMENT_CHANNELS)
class BillsPaymentChannels {
    data class Input(
        val Bills: List<String>,
        val Service: String
    )

    data class Output(
        val PaymentChannels: List<PaymentChannel>
    )
}