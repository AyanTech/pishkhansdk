package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentTransactionBinding
import ir.ayantech.pishkhansdk.helper.extensions.getFormattedPersianDateTime
import ir.ayantech.pishkhansdk.model.api.UserTransactions
import ir.ayantech.pishkhansdk.model.app_logic.getCardHistoryIcon
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.pishkhansdk.ui.components.setArrowIvTint
import ir.ayantech.pishkhansdk.ui.components.setDescriptionTextColor
import ir.ayantech.pishkhansdk.ui.components.setRightIndicatorIvTint
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener
import ir.ayantech.whygoogle.helper.formatAmount
import ir.ayantech.whygoogle.helper.isNull

class TransactionAdapter(
    val showingIcon: Boolean = false,
    items: List<UserTransactions.Transaction>,
    onItemClickListener: OnItemClickListener<UserTransactions.Transaction>
) : CommonAdapter<UserTransactions.Transaction, PishkhansdkComponentTransactionBinding>(
    items,
    onItemClickListener
) {

    override fun onBindViewHolder(
        holder: CommonViewHolder<UserTransactions.Transaction, PishkhansdkComponentTransactionBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.accessViews {
            val item = itemsToView[position]
            this.init(
                showingIcon = showingIcon,
                title = item.Title ?: "",
                amount = item.Amount.formatAmount(),
                description = if (item.PaymentChannel.isNull()) "" else
                    item.PaymentChannel?.ShowName + " - " + item.PaymentGateway.ShowName,
                note = item.Description ?: "",
                date = item.DateTime?.getFormattedPersianDateTime(),
                icServiceRecourse = item.Type.Name.getCardHistoryIcon()
            )

            this.setDescriptionTextColor(checkColors(itemsToView[position]))

            this.setArrowIvTint(checkColors(itemsToView[position]))

            this.setRightIndicatorIvTint(checkColors(itemsToView[position]))
        }
    }

    fun updateList(list: List<UserTransactions.Transaction>) {
        this.itemsToView = list
        this.items = list
        notifyDataSetChanged()
    }

    private fun checkColors(transaction: UserTransactions.Transaction): Int {
        return when (transaction.Type.Name) {
            "walletCharge" -> R.color.green
            "sadadWalletCharge" -> R.color.green
            else -> {
                if (transaction.PaymentChannel?.Name == "OnlinePayment")
                    R.color.color_secondary
                else
                    R.color.orange
            }
        }
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkComponentTransactionBinding
        get() = PishkhansdkComponentTransactionBinding::inflate
}