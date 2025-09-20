package ir.ayantech.pishkhansdk.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkRowWalletPaymentChannelsBinding
import ir.ayantech.pishkhansdk.helper.extensions.changeEnable
import ir.ayantech.pishkhansdk.helper.extensions.loadFromString
import ir.ayantech.pishkhansdk.model.app_logic.PaymentChannel
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener
import ir.ayantech.whygoogle.helper.changeVisibility

class PishkhansdkWalletPaymentChannelsAdapter(
    items: List<PaymentChannel>,
    onItemClickListener: OnItemClickListener<PaymentChannel>
): CommonAdapter<PaymentChannel, PishkhansdkRowWalletPaymentChannelsBinding>(
    items, onItemClickListener
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PishkhansdkRowWalletPaymentChannelsBinding
        get() = PishkhansdkRowWalletPaymentChannelsBinding::inflate

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: CommonViewHolder<PaymentChannel, PishkhansdkRowWalletPaymentChannelsBinding>,
        position: Int,
        payloads: List<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        holder.accessViews {
            val currentPaymentChannel = itemsToView[position]
            currentPaymentChannel.Gateways?.firstOrNull()?.let { gateway ->
                paymentChannelShowNameTv.text = gateway.Type.ShowName
                gateway.Icon?.let { paymentChannelIconIv.loadFromString(it) }
            }
            paymentChannelShowNameTv.isSelected = true
            spacer.changeVisibility(show = position != (itemsToView.size - 1))
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
            paymentChannelRl.setBackgroundResource(if (currentPaymentChannel.Selected) R.drawable.pishkhansdk_row_payment_channels_root_background_selected else R.drawable.pishkhansdk_row_payment_channels_root_background_deselected)
        }
    }

    fun setSelectedItem(item: PaymentChannel) {
        items.forEach { it.Selected = false }
        items.firstOrNull { it.Type.Name == item.Type.Name }?.Selected = true
        itemsToView = items
        notifyDataSetChanged()
    }
}