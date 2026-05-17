package ir.ayantech.pishkhansdk.ui.components

import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentExtraInfoBinding
import ir.ayantech.pishkhansdk.helper.extensions.setEndDrawable
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.model.component_data_model.PishkhansdkExtraInfoComponentDataModel
import ir.ayantech.pishkhansdk.ui.adapter.PishkhansdkExtraInfoComponentItemsAdapter
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.delayedTransition
import ir.ayantech.whygoogle.helper.isVisible
import ir.ayantech.whygoogle.helper.safeGet
import ir.ayantech.whygoogle.helper.verticalSetup

fun PishkhansdkComponentExtraInfoBinding.initExtraInfoComponent(data: PishkhansdkExtraInfoComponentDataModel) {

    data.firstRow?.let { extraInfo ->
        firstKeyValueLayout.keyTv.text = extraInfo.Key
        firstKeyValueLayout.valueTv.text = extraInfo.Value
    }

    data.secondRow?.let { extraInfo ->
        secondKeyValueLayout.keyTv.text = extraInfo.Key
        secondKeyValueLayout.valueTv.text = extraInfo.Value
    }

    detailTv.changeVisibility(show = !data.extraInfoItems.isNullOrEmpty())
    detailTv.setOnClickListener {
        rootRl.delayedTransition()
        extraInfoRv.changeVisibility(show = !extraInfoRv.isVisible())
        detailTv.setEndDrawable(if (extraInfoRv.isVisible()) R.drawable.pishkhansdk_ic_arrow_up else R.drawable.pishkhansdk_ic_arrow_down)
    }
    if (!data.extraInfoItems.isNullOrEmpty()) {
        extraInfoRv.apply {
            verticalSetup()
            adapter = PishkhansdkExtraInfoComponentItemsAdapter(items = data.extraInfoItems)
        }
    }
}