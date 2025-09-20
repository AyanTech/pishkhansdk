package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.databinding.PishkhansdkRowSuggestionAmountsBinding
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.formatAmount

class PishkhansdkSuggestionAmountsAdapter(
    items: List<Long>,
    onItemClickListener: OnItemClickListener<Long>
): CommonAdapter<Long, PishkhansdkRowSuggestionAmountsBinding>(
    items, onItemClickListener
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkRowSuggestionAmountsBinding
        get() = PishkhansdkRowSuggestionAmountsBinding::inflate

    override fun onBindViewHolder(
        holder: CommonViewHolder<Long, PishkhansdkRowSuggestionAmountsBinding>,
        position: Int,
        payloads: List<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        holder.accessViews {
            val currentItem = itemsToView[position]
            amountTv.text = currentItem.formatAmount()
            amountTv.isSelected = true
            spacer.changeVisibility(show = position != (itemsToView.size - 1))
        }
    }
}