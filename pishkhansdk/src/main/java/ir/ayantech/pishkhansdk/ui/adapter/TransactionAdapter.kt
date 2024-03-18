package ir.ayantech.pishkhansdk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentTransactionBinding
import ir.ayantech.pishkhansdk.helper.extensions.getFormattedPersianDateTime
import ir.ayantech.pishkhansdk.model.api.UserTransactions
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
            this.init(
                title = itemsToView[position].Title ?: "",
                amount = itemsToView[position].Amount.formatAmount(),
                description = if (itemsToView[position].PaymentChannel.isNull()) "" else
                    itemsToView[position].PaymentChannel?.ShowName + " - " + itemsToView[position].PaymentGateway.ShowName,
                note = itemsToView[position].Description ?: "",
                date = itemsToView[position].DateTime?.getFormattedPersianDateTime()
            )

            this.setDescriptionTextColor(checkColors(itemsToView[position]))

            this.setArrowIvTint(checkColors(itemsToView[position]))

            this.setRightIndicatorIvTint(checkColors(itemsToView[position]))
        }
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