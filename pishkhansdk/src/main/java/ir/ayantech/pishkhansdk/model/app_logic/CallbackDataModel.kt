package ir.ayantech.pishkhansdk.model.app_logic

import android.os.Parcel
import android.os.Parcelable
import ir.ayantech.pishkhansdk.PishkhanUser
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.AMOUNT
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.BILLS
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.CHANNEL_NAME
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.DATA_ID
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.PURCHASE_KEY
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.SELECTED_GATEWAY
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.SERVICE_NAME
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.SOURCE_PAGE
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.USE_CREDIT
import ir.ayantech.pishkhansdk.model.app_logic.CallbackDataModel.Params.VOUCHER_CODE
import ir.ayantech.whygoogle.helper.isNotNull
import java.net.URLEncoder

data class CallbackDataModel(
    var sourcePage: String?,
    var purchaseKey: String?,
    var paymentStatus: String? = null,
    var selectedGateway: String?,
    var serviceName: String? = null,
    var useCredit: String? = null,
    var voucherCode: String? = null,
    var channelName: String? = null,
    var bills: List<String>? = null,
    var amount: String? = null,
    var dataId: String? = null,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(sourcePage)
        dest.writeString(purchaseKey)
        dest.writeString(paymentStatus)
        dest.writeString(selectedGateway)
        dest.writeString(serviceName)
        dest.writeString(useCredit)
        dest.writeString(voucherCode)
        dest.writeString(channelName)
    }

    companion object CREATOR : Parcelable.Creator<CallbackDataModel> {
        override fun createFromParcel(parcel: Parcel): CallbackDataModel {
            return CallbackDataModel(parcel)
        }

        override fun newArray(size: Int): Array<CallbackDataModel?> {
            return arrayOfNulls(size)
        }
    }

    fun createCallBackLink(): String {
        val params = mutableListOf<String>()

        fun addParam(key: String, value: String?) {
            if (value.isNullOrEmpty().not()) {
                params.add("$key=$value")
            }
        }

        addParam(SOURCE_PAGE, sourcePage)
        addParam(PURCHASE_KEY, purchaseKey)

        if (bills.isNullOrEmpty().not()) {
            val joined = bills!!.joinToString(",")
            val encoded = URLEncoder.encode(joined, "UTF-8")
            addParam(BILLS, encoded)
        }

        addParam(SELECTED_GATEWAY, selectedGateway)
        addParam(SERVICE_NAME, serviceName)
        addParam(USE_CREDIT, useCredit)
        addParam(VOUCHER_CODE, voucherCode)
        addParam(CHANNEL_NAME, channelName)
        addParam(AMOUNT, amount)
        addParam(DATA_ID, dataId)

        return "${PishkhanUser.prefix}${params.joinToString("&")}&paymentStatus=#status#"
    }

    object Params {
        const val BILLS = "bills"
        const val SOURCE_PAGE = "sourcePage"
        const val PURCHASE_KEY = "purchaseKey"
        const val SELECTED_GATEWAY = "selectedGateway"
        const val PAYMENT_STATUS = "paymentStatus"
        const val SERVICE_NAME = "serviceName"
        const val USE_CREDIT = "useCredit"
        const val VOUCHER_CODE = "voucherCode"
        const val CHANNEL_NAME = "channelName"
        const val AMOUNT = "amount"
        const val DATA_ID = "dataID"
    }
}

//fun CallbackDataModel.createCallBackLink(
//): String {
//    var result = "${PishkhanUser.schema}://${PishkhanUser.host}?"
//    PishkhanUser.prefix = "${PishkhanUser.schema}://${PishkhanUser.host}?"
//    if (this.sourcePage.isNotNull())
//        result += "sourcePage=${this.sourcePage}"
//    if (this.purchaseKey.isNotNull())
//        result = result + "&" + "purchaseKey=${this.purchaseKey}"
//    if (this.selectedGateway.isNotNull())
//        result = result + "&" + "selectedGateway=${this.selectedGateway}"
//    if (paymentStatus.isNotNull())
//        result = result + "&" + "paymentStatus=${this.paymentStatus}"
//    if (serviceName.isNotNull())
//        result = result + "&" + "serviceName=${this.serviceName}"
//    if (useCredit.isNotNull())
//        result = result + "&" + "useCredit=${this.useCredit}"
//    if (voucherCode.isNotNull())
//        result = result + "&" + "voucherCode=${this.voucherCode}"
//    if (channelName.isNotNull())
//        result = result + "&" + "channelName=${this.channelName}"
//
//    return result
//}