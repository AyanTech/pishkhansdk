package ir.ayantech.pishkhansdk.ui.components

import ir.ayantech.pishkhansdk.databinding.ComponentDirectDebitCardBinding
import ir.ayantech.pishkhansdk.helper.extensions.textColor
import ir.ayantech.pishkhansdk.helper.setTint

fun ComponentDirectDebitCardBinding.init(
    title: String,
    amount: String,
    description: String,
    note: String,
    date: String?,
) {
    titleTv.text = title
    amountTv.text = amount
    descriptionTv.text = description
    noteTv.text = note
    dateTv.text = date
}

fun ComponentDirectDebitCardBinding.setDescriptionTextColor(color: Int) {
    descriptionTv.textColor(color)
}

fun ComponentDirectDebitCardBinding.setArrowIvTint(color: Int) {
    arrowIv.setTint(color)
}

fun ComponentDirectDebitCardBinding.setRightIndicatorIvTint(color: Int) {
    rightIndicatorIv.setTint(color)
}
