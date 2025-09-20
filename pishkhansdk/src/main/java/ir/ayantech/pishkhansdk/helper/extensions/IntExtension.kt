package ir.ayantech.pishkhansdk.helper.extensions

import ir.ayantech.whygoogle.helper.formatAmount

fun Int.formatAmount(unit: String = "ریال") = this.toString().formatAmount(unit)