package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.databinding.PishkhansdkRowExtraInfoBinding
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder

class PishkhansdkExtraInfoComponentItemsAdapter(
    items: List<ExtraInfo>
): CommonAdapter<ExtraInfo, PishkhansdkRowExtraInfoBinding>(
    items
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkRowExtraInfoBinding
        get() = PishkhansdkRowExtraInfoBinding::inflate

    override fun onBindViewHolder(
        holder: CommonViewHolder<ExtraInfo, PishkhansdkRowExtraInfoBinding>,
        position: Int,
        payloads: List<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        holder.accessViews {
            val currentExtraInfoItem = itemsToView[position]
            keyTv.text = currentExtraInfoItem.Key
            valueTv.text = currentExtraInfoItem.Value
        }
    }
}