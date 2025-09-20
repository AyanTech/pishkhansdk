package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.SHAPARAK_BANK_LIST_SERVICE)
class ShaparakBankListService {
    data class Output(
        val AllList: List<ShaparakBank>,
        val SupportedList: List<ShaparakBank>
    )

    data class ShaparakBank(
        val BillPaymentOtpCodeRegex: String,
        val CardTransferOtpCodeRegex: String,
        val Code: List<String>,
        val ID: Long,
        val Icon: String,
        val Name: String,
        val OtpCodeRegex: String,
        val PurchaseOtpCodeRegex: String,
        val ShowName: String
    )
}