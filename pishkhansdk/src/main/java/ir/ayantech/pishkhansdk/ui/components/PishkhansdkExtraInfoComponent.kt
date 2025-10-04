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

    data.extraInfoItems.safeGet(0)?.let { extraInfo ->
        firstKeyValueLayout.keyTv.text = extraInfo.Key
        firstKeyValueLayout.valueTv.text = extraInfo.Value
    }

    data.extraInfoItems.safeGet(1)?.let { extraInfo ->
        secondKeyValueLayout.keyTv.text = extraInfo.Key
        secondKeyValueLayout.valueTv.text = extraInfo.Value
    }

    detailTv.changeVisibility(show = data.extraInfoItems.size > 2)
    detailTv.setOnClickListener {
        rootRl.delayedTransition()
        extraInfoRv.changeVisibility(show = !extraInfoRv.isVisible())
        detailTv.setEndDrawable(if (extraInfoRv.isVisible()) R.drawable.pishkhansdk_ic_arrow_up else R.drawable.pishkhansdk_ic_arrow_down)
    }
    if (data.extraInfoItems.size > 2) {
        val subList = mutableListOf<ExtraInfo>()
        subList.addAll(data.extraInfoItems)
        subList.removeAt(0)
        subList.removeAt(0)
        extraInfoRv.apply {
            verticalSetup()
            adapter = PishkhansdkExtraInfoComponentItemsAdapter(items = subList)
        }
    }
}