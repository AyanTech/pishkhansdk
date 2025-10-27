package ir.ayantech.pishkhansdk.model.api

class TransferTaxV2Output {

    data class Result(
        val Amount: Long,
        val DateTime: String,
        val Payment: Payment,
        val Type: Type,
        val UniqueID: String,
        val ExtraInfo: ExtraInfo,
    )

    data class PaymentExtraInfo(
        val DateTime: String,
        val SettlementCertificateUrl: String,
        val TraceNumber: String,
        val TransactionUniqueID: String,
    )

    data class Status(
        val Name: String,
        val ShowName: String,
    )

    data class Payment(
        val DeadLine: String?,
        val ExtraInfo: PaymentExtraInfo?,
        val Status: Status,
    )

    data class Type(
        val Name: String,
        val ShowName: String,
    )

    data class ExtraInfo(
        val HasSettlementCertificate: Boolean,
        val OwnerName: String,
        val VehicleChassisNumber: String,
        val VehicleEngineNumber: String,
        val VehicleFuelType: String,
        val VehicleModel: String,
        val VehicleSystem: String,
        val VehicleTip: String,
        val VehicleUsage: String,
    )
}