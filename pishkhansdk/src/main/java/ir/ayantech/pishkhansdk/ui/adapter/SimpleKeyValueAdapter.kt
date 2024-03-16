package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentTableRowsBinding
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener

class SimpleKeyValueAdapter(
    items: List<ExtraInfo>,
    private val highlight: Boolean = true,
    private val highlightColor: Int = R.color.color_secondary_alpha5,
    private val startHighlightFromZero: Boolean = true,
    private val keyTvResId: Int = R.style.key_text_style,
    private val valueTvResId: Int = R.style.value_text_style,
    onItemClickListener: OnItemClickListener<ExtraInfo>? = null
) : CommonAdapter<ExtraInfo, PishkhansdkComponentTableRowsBinding>(items, onItemClickListener) {


    override fun onBindViewHolder(
        holder: CommonViewHolder<ExtraInfo, PishkhansdkComponentTableRowsBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.accessViews {
            this.init(
                key = itemsToView[position].Key,
                value = itemsToView[position].Value,
                buttonText = itemsToView[position].buttonText,
                buttonIcon = itemsToView[position].buttonIcon,
                keyTvResId = keyTvResId,
                valueTvResId = valueTvResId,
                highlight = highlight,
                startHighlightFromZero = startHighlightFromZero,
                position = position,
                highlightColor = highlightColor,
                valueTextColor = itemsToView[position].textColor,
                onActionButtonClicked = itemsToView[position].onActionButtonClicked
            )
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkComponentTableRowsBinding
        get() = PishkhansdkComponentTableRowsBinding::inflate
}