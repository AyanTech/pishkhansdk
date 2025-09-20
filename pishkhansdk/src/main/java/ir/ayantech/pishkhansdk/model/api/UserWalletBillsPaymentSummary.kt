package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.USER_BILLS_PAYMENT_SUMMARY)
class UserBillsPaymentSummary {

    class Input(
        val BillUniqueID: List<String>,
        val GatewayName: List<String>
    )

    class Output(val Result: List<PaymentSummary>)

}
