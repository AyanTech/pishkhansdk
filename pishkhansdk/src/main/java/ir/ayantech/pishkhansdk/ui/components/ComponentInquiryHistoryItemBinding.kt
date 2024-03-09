package ir.ayantech.pishkhansdk.ui.components

import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkComponentInquiryHistoryItemBinding

fun PishkhansdkComponentInquiryHistoryItemBinding.init(
    title: String,
    description: String,
    isFavorite: Boolean
) {
    titleTv.text = title
    descriptionTv.text = description
    favoriteIv.setBackgroundResource(
        if (isFavorite) R.drawable.back_is_favorite_icon
        else R.drawable.back_is__not_favorite_icon
    )
}