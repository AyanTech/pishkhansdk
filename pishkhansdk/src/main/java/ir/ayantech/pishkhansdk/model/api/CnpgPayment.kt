package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints


@AyanAPI(endPoint = EndPoints.CNPG_PAYMENT)
class CnpgPayment {
    data class Input(val PaymentKey: String, val EncryptData: String)
    data class Output(val TransactionDateTime: String, val TransactionTraceNumber: String)
}