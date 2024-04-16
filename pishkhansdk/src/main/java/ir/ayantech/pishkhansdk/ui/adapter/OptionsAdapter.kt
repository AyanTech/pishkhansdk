package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.databinding.RowMessageButtonBinding
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener

class OptionsAdapter(
    items: List<Type>,
    onItemClickListener: OnItemClickListener<Type>
) :
    CommonAdapter<Type, RowMessageButtonBinding>(items, onItemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<Type, RowMessageButtonBinding> {
        return super.onCreateViewHolder(parent, viewType).also {
            it.registerClickListener(it.mainView.btn)
        }
    }

    override fun onBindViewHolder(
        holder: CommonViewHolder<Type, RowMessageButtonBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.accessViews {
            btn.text = itemsToView[position].ShowName
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RowMessageButtonBinding
        get() = RowMessageButtonBinding::inflate
}