package ir.ayantech.pishkhansdk.helper.extensions

import com.google.gson.JsonParser
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.whygoogle.helper.formatAmount
import java.security.MessageDigest


fun String.sha1Hash(): String {
    return MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
        .joinToString("") { "%02x".format(it) }
}

fun String.takeFirstNCharacter(characterCount: Int): String {
    return this.substring(0, Math.min(this.length, characterCount))
}

fun String.getRawAmount(): Long {
    if (this.isEmpty()) return 0
    return this.replace(",", "").replace(" ریال", "").toLong()
}

fun String.getCurrencyValue() =
    this.replace(" ", "").replace(",", "").replace("ریال", "").replace("۰", "0")
        .replace("۱", "1")
        .replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5")
        .replace("۶", "6")
        .replace("۷", "7").replace("۸", "8").replace("۹", "9").toLong()

fun String.simpleUnify() = this.replace('ي', 'ی').replace('ك', 'ک')

fun String.getKeyValueExtraInfo(isMoneyValues: Boolean = false): List<ExtraInfo> {
    if (this.isEmpty()) return arrayListOf()
    val result = ArrayList<ExtraInfo>()
    val extraInfoObject = JsonParser().parse(this).asJsonObject
    extraInfoObject.keySet().forEach {
        val value = extraInfoObject[it].toString().replace("\"", "")
        result.add(
            ExtraInfo(
                Key = it,
                Value = if (isMoneyValues) value.formatAmount() else value
            )
        )
    }
    return result
}

fun hexToArgb(hexColor: String, alpha: Int): Int {
// Ensure that the alpha value is within the valid range (0 to 255)
    val adjustedAlpha = alpha.coerceIn(0, 255)

    // Remove '#' from the hexadecimal color code
    val cleanHexColor = hexColor.removePrefix("#")

    // Parse the cleaned hex color code and extract RGB values
    val color = cleanHexColor.toLong(16)
    val red = (color shr 16 and 0xFF).toInt()
    val green = (color shr 8 and 0xFF).toInt()
    val blue = (color and 0xFF).toInt()
    // Combine the RGB values with the alpha value
    return adjustedAlpha shl 24 or (red shl 16) or (green shl 8) or blue
}
