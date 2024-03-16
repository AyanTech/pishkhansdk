package ir.ayantech.pishkhansdk.ui.components

import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentInquiryHistoryItemBinding
import ir.ayantech.pishkhansdk.helper.extensions.setTint

fun PishkhansdkComponentInquiryHistoryItemBinding.init(
    title: String,
    description: String,
    isFavorite: Boolean
) {
    titleTv.text = title
    descriptionTv.text = description
    favoriteIv.setTint(
        if (isFavorite) R.drawable.back_is_favorite_icon
        else R.drawable.back_is_not_favorite_icon
    )
}