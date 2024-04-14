package ir.ayantech.pishkhansdk.model.app_logic


import ir.ayantech.whygoogle.helper.SimpleCallBack

data class Type(
    val Name: String,
    val ShowName: String
) {
    val validForPayment: Boolean
        get() = this.Name == "Unpaid"
}

data class ExtraInfo(
    val Key: String,
    var Value: String? = null,
    var textColor: Int = 0,
    val buttonText: String? = null,
    val buttonIcon: Int? = null,
    val onActionButtonClicked: SimpleCallBack? = null
)

data class Gateway(
    val Active: Boolean,
    val Icon: String?,
    val Type: Type,
    var Selected: Boolean = false,
    //if of type wallet payment channel assign balance
    var Balance: Long = 0,
    var BalanceApiFailed: Boolean,
    var BalanceApiLoading: Boolean
)

data class PaymentChannel(
    val Active: Boolean,
    val Default: Boolean,
    var Gateways: List<Gateway>?,
    val Type: Type,
    var Selected: Boolean = false,
    var temporaryUnavailable: Boolean
)

data class Source(
    val Title: String?,
    val Url: String?
)

data class OTP(
    val Length: Long,
    val Timer: Double,
    val Message: String?,
    val ReferenceNumber: String?,
    val Validation: String?
)

data class Action(
    val Type: String,
    val Value: String?
)

data class Invoice(
    val Amount: Long,
    val Discount: Long,
    val ExtraInfoDictionary: List<ExtraInfo>?,
    val Payable: Long,
    val PurchaseKey: String,
    val Service: Service,
    val Status: Type
)

data class Service(
    val Price: Long,
    val Summary: List<ExtraInfo>?,
    val Tax: Long,
    val Type: Type
)

data class Payment(
    val DeadLine: String?,
    val ExtraInfo: PaymentExtraInfo?,
    val Status: Type
)

data class PaymentExtraInfo(
    val CardPAN: String,
    val DateTime: String,
    val ReferenceNumber: String,
    val TraceNumber: String,
    val TransactionUniqueID: String
)

data class Button(
    val Action: Action,
    val Title: String
)

data class IbanResult(
    val Bank: Bank?,
    val ExtraInfo: String?,
    val Value: String
)

data class Bank(
    val AccountDelimiter: String,
    val AccountDelimiterArray: List<Long>,
    val IbanFromAccountNumber: Boolean,
    val IbanFromCardNumber: Boolean,
    val IbanToAccountNumber: Boolean,
    val Icon: String,
    val IsActive: Boolean,
    val Name: String,
    val ShowName: String,
    val IconWhite: String,
    val ColorCode: String
)

data class CardInfo(
    val BankName: String?,
    val BankIcon: String?,
    val Number: String?,
    val Color: String?
)