package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserServiceQueries)
class UserServiceQueries {
    data class Input(
        val Service: String
    )

    class Output : ArrayList<InquiryHistory>()

    data class InquiryHistory(
        val AutoFill: Boolean,
        var Favorite: Boolean,
        val Index: String?,
        var Note: String?,
        val Parameters: List<ExtraInfo>,
        val UniqueID: String?
    )
}