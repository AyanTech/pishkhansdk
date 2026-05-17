package ir.ayantech.pishkhansdk.model.component_data_model

import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo

data class PishkhansdkExtraInfoComponentDataModel(
    val firstRow: ExtraInfo?,
    var secondRow: ExtraInfo? = null,
    val extraInfoItems: List<ExtraInfo>?
) {

    @Deprecated(message = "Use constructor with three parameters.")
    constructor(extraInfoItems: List<ExtraInfo>): this(firstRow = null, secondRow = null, extraInfoItems)

}