package ir.ayantech.pishkhansdk.ui.components

import android.view.View
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentTransactionBinding
import ir.ayantech.pishkhansdk.helper.extensions.textColor
import ir.ayantech.pishkhansdk.helper.extensions.setTint

fun PishkhansdkComponentTransactionBinding.init(
    showingIcon: Boolean = false,
    title: String,
    amount: String,
    description: String,
    note: String,
    date: String?,
    icServiceRecourse: Int
) {
    titleTv.text = title
    amountTv.text = amount
    descriptionTv.text = description
    noteTv.text = note
    dateTv.text = date
    if (showingIcon)
        icService.setImageResource(icServiceRecourse)
    else
        icService.visibility = View.GONE
}

fun PishkhansdkComponentTransactionBinding.setDescriptionTextColor(color: Int) {
    descriptionTv.textColor(color)
}

fun PishkhansdkComponentTransactionBinding.setArrowIvTint(color: Int) {
    arrowIv.setTint(color)
}

fun PishkhansdkComponentTransactionBinding.setRightIndicatorIvTint(color: Int) {
    rightIndicatorIv.setTint(color)
}