package ir.ayantech.pishkhansdk.helper.extensions

import android.util.Base64
import com.google.gson.JsonParser
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.whygoogle.helper.formatAmount
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


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

fun String.encryptData(pk: String): String {
    var encoded = ""
    val encrypted: ByteArray?
    try {
        val publicBytes: ByteArray = Base64.decode(pk, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val pubKey: PublicKey = keyFactory.generatePublic(keySpec)
        val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        encrypted = cipher.doFinal(this.toByteArray())
        encoded = Base64.encodeToString(encrypted, Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return encoded
}

fun String.reformatAmountToNumber() : String {
    return this.replace("ریال", "").replace(",", "").trim()
}

fun String.addChar(char: Char, every: Int) : String {
    var newStr = ""
    toCharArray().forEachIndexed { index, c ->
        newStr += c
        if ((index + 1) % every == 0 && index != (length - 1))
            newStr += char
    }
    return newStr
}

fun String.extractOtp(pattern: String): String {
    val otpLine = Regex(pattern).find(this)?.value.orEmpty()
    return Regex("\\d+").findAll(otpLine)
        .map { it.value }
        .joinToString("")
}