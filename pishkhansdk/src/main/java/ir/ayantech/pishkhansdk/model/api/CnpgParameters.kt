package ir.ayantech.pishkhansdk.model.api

import ir.ayantech.pishkhansdk.helper.extensions.encryptData
import ir.ayantech.whygoogle.helper.toJsonString

data class CnpgRequestParameters(
    val Pan: String,
    val ExpireDate: String?,
    val Cvv2: String?,
    val Pin: String?,
    val SaveCard: Boolean?,
    val TraceNumber: String
)

fun CnpgRequestParameters.encryptData(
    publicKey: String
): String {
    return this.toJsonString().encryptData(publicKey)
}