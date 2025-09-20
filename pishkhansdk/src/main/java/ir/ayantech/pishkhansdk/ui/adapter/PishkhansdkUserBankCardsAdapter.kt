package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.databinding.PishkhansdkRowUserBankCardsBinding
import ir.ayantech.pishkhansdk.helper.extensions.loadFromString
import ir.ayantech.pishkhansdk.model.api.CnpgGetCardList
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener

class PishkhansdkUserBankCardsAdapter(
    items: List<CnpgGetCardList.BankCard>,
    private val onDeleteIvClicked: (bankCard: CnpgGetCardList.BankCard) -> Unit,
    onItemClickListener: OnItemClickListener<CnpgGetCardList.BankCard>
): CommonAdapter<CnpgGetCardList.BankCard, PishkhansdkRowUserBankCardsBinding>(
    items, onItemClickListener
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkRowUserBankCardsBinding
        get() = PishkhansdkRowUserBankCardsBinding::inflate

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<CnpgGetCardList.BankCard, PishkhansdkRowUserBankCardsBinding> {
        return super.onCreateViewHolder(parent, viewType).also { holder ->
            holder.registerClickListener(holder.mainView.deleteBankCardIv) {
                onDeleteIvClicked.invoke(itemsToView[holder.bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(
        holder: CommonViewHolder<CnpgGetCardList.BankCard, PishkhansdkRowUserBankCardsBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.accessViews {
            val currentBankCardItem = itemsToView[position]
            currentBankCardItem.Icon?.let { bankIconIv.loadFromString(it) }
            bankNameTv.text = currentBankCardItem.BankName
            cardNumberTv.text = currentBankCardItem.MaskedPan
        }
    }

    fun updateItems(bankCards: List<CnpgGetCardList.BankCard>) {
        items = bankCards
        itemsToView = items
        notifyDataSetChanged()
    }
}