package ir.ayantech.pishkhansdk.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkRowPaymentChannelsBinding
import ir.ayantech.pishkhansdk.helper.extensions.changeEnable
import ir.ayantech.pishkhansdk.helper.extensions.loadFromString
import ir.ayantech.pishkhansdk.helper.payment.channels.PaymentChannels
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.formatAmount

class PishkhansdkPaymentChannelsAdapter(
    items: List<PaymentChannel>,
    onItemClickListener: OnItemClickListener<PaymentChannel>
): CommonAdapter<PaymentChannel, PishkhansdkRowPaymentChannelsBinding>(
    items = items, onItemClickListener = onItemClickListener
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkRowPaymentChannelsBinding
        get() = PishkhansdkRowPaymentChannelsBinding::inflate

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: CommonViewHolder<PaymentChannel, PishkhansdkRowPaymentChannelsBinding>,
        position: Int,
        payloads: List<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        val currentPaymentChannel = itemsToView[position]
        holder.accessViews {
            root.changeEnable(isEnable = currentPaymentChannel.Active)
            root.isSelected = currentPaymentChannel.Active
            selectionRb.isChecked = currentPaymentChannel.Selected
            selectionRb.changeEnable(isEnable = currentPaymentChannel.Active)
            selectionRb.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    onItemClickListener?.invoke(currentPaymentChannel, getItemId(position).toInt(), position)
                }
                true
            }
            dataRootRl.setBackgroundResource(if (currentPaymentChannel.Selected) R.drawable.pishkhansdk_row_payment_channels_root_background_selected else R.drawable.pishkhansdk_row_payment_channels_root_background_deselected)
            channelNameTv.text = currentPaymentChannel.Type.ShowName
            val paymentChannelIsWallet = currentPaymentChannel.Type.Name == PaymentChannels.Wallet.name
            if (paymentChannelIsWallet) {
                gatewayNameTv.text = holder.itemView.context.getString(R.string.wallet_balance)
            } else {
                gatewayNameTv.text = currentPaymentChannel.Gateways?.firstOrNull()?.Type?.ShowName ?: ""
            }
            currentPaymentChannel.Gateways?.firstOrNull()?.Icon?.let { iconIv.loadFromString(it) }
            walletTv.changeVisibility(show = paymentChannelIsWallet)
            if (currentPaymentChannel.isWalletBalanceSufficient) {
                walletTv.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.pishkhansdk_success)))
                walletTv.text = holder.itemView.context.getString(R.string.wallet_balance_is_sufficient, currentPaymentChannel.walletBalance.formatAmount())
            } else {
                walletTv.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.pishkhansdk_error)))
                walletTv.text = holder.itemView.context.getString(R.string.wallet_balance_is_not_sufficient, currentPaymentChannel.walletBalance.formatAmount())
            }
        }
    }

    fun setSelectedItem(item: PaymentChannel) {
        items.forEach { it.Selected = false }
        items.firstOrNull { it.Type.Name == item.Type.Name }?.Selected = true
        itemsToView = items
        notifyDataSetChanged()
    }

}