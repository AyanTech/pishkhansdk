package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentInquiryHistoryItemBinding
import ir.ayantech.pishkhansdk.model.api.UserServiceQueries
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener

class InquiryHistoryAdapter(
    items: List<UserServiceQueries.InquiryHistory>,
    onItemClickListener: OnItemClickListener<UserServiceQueries.InquiryHistory>
) : CommonAdapter<UserServiceQueries.InquiryHistory, PishkhansdkComponentInquiryHistoryItemBinding>(
    items,
    onItemClickListener
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<UserServiceQueries.InquiryHistory, PishkhansdkComponentInquiryHistoryItemBinding> {
        return super.onCreateViewHolder(parent, viewType).also {
            it.registerClickListener(it.mainView.deleteIv)
            it.registerClickListener(it.mainView.editIv)
            it.registerClickListener(it.mainView.favoriteIv)
        }
    }

    override fun onBindViewHolder(
        holder: CommonViewHolder<UserServiceQueries.InquiryHistory, PishkhansdkComponentInquiryHistoryItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.accessViews {
            this.init(
                title = itemsToView[position].Note ?: "",
                description = itemsToView[position].Index!!,
                isFavorite = itemsToView[position].Favorite
            )
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkComponentInquiryHistoryItemBinding
        get() = PishkhansdkComponentInquiryHistoryItemBinding::inflate
}