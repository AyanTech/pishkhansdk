package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.helper.extensions.getKeyValueExtraInfo
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.model.app_logic.Messages
import ir.ayantech.pishkhansdk.model.app_logic.Prerequisites
import ir.ayantech.pishkhansdk.model.app_logic.Query
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.whygoogle.helper.isNotNull

@AyanAPI(endPoint = EndPoints.PostPackagesStatus)
class PostPackagesStatus {

    class Input(
        OTPCode: String? = null,
        PurchaseKey: String?,
        val TrackingCode: String
    ) : BaseInputModel(OTPCode = OTPCode, PurchaseKey = PurchaseKey)

    open class Output(
        Prerequisites: Prerequisites?,
        Messages: Messages?,
        Query: Query,
        Result: PackageTrackingStatusResult?,
        WarningMessage: String
    ) : BaseResultModel<PackageTrackingStatusResult>(
        Query = Query,
        Result = Result,
        WarningMessage = WarningMessage,
        Prerequisites = Prerequisites,
        Messages = Messages
    )

    data class PackageTrackingStatusResult(
        val DateTimeAcceptance: String?,
        val Destination: String,
        val Events: List<Event>?,
        val ExtraInfo: String,
        val InsuranceCost: String,
        val PackageNumber: String,
        val PostCost: String,
        val ReceiverName: String,
        val ReceiverZip: String,
        val SenderName: String,
        val SenderZip: String,
        val ServiceType: String,
        val Source: String,
        val SourcePostOffice: String,
        val Weight: String
    )

    data class Event(
        val DateTime: String?,
        val EventNumber: String,
        val ExtraInfo: String?,
        val Province: String
    ) {
        fun getEventShowKeyValue(): List<ExtraInfo> {
            val result = arrayListOf<ExtraInfo>()
            result.add(ExtraInfo("تاریخ و ساعت", if (DateTime.isNotNull()) DateTime else ""))
            result.add(ExtraInfo("محل", Province))
            ExtraInfo?.getKeyValueExtraInfo()?.let { result.addAll(it) }
            return result
        }
    }


}