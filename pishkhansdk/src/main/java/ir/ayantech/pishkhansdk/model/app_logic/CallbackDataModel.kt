package ir.ayantech.pishkhansdk.model.app_logic

import android.os.Parcel
import android.os.Parcelable
import ir.ayantech.pishkhansdk.PishkhanUser
import ir.ayantech.whygoogle.helper.isNotNull

data class CallbackDataModel(
    var sourcePage: String?,
    var purchaseKey: String?,
    var paymentStatus: String?,
    var selectedGateway: String?,
    var serviceName: String? = null,
    var useCredit: String? = null,
    var voucherCode: String? = null,
    var channelName: String? = null,
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
}

fun CallbackDataModel.createCallBackLink(
): String {
    var result = "${PishkhanUser.schema}://${PishkhanUser.host}?"
    PishkhanUser.prefix = "${PishkhanUser.schema}://${PishkhanUser.host}?"
    if (this.sourcePage.isNotNull())
        result += "sourcePage=${this.sourcePage}"
    if (this.purchaseKey.isNotNull())
        result = result + "&" + "purchaseKey=${this.purchaseKey}"
    if (this.selectedGateway.isNotNull())
        result = result + "&" + "selectedGateway=${this.selectedGateway}"
    if (paymentStatus.isNotNull())
        result = result + "&" + "paymentStatus=${this.paymentStatus}"
    if (serviceName.isNotNull())
        result = result + "&" + "serviceName=${this.serviceName}"
    if (useCredit.isNotNull())
        result = result + "&" + "useCredit=${this.useCredit}"
    if (voucherCode.isNotNull())
        result = result + "&" + "voucherCode=${this.voucherCode}"
    if (channelName.isNotNull())
        result = result + "&" + "channelName=${this.channelName}"

    return result
}
