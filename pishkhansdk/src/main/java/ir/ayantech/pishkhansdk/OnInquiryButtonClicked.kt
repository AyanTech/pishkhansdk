package ir.ayantech.pishkhansdk

import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.FailureCallback
import ir.ayantech.pishkhan24.model.api.BaseInputModel
import ir.ayantech.whygoogle.activity.WhyGoogleActivity

fun onInquiryButtonClicked(
    activity: WhyGoogleActivity<*>,
    inputModel: BaseInputModel,
    productName: String,
    servicesPishkhan24Api: AyanApi,
    corePishkhan24Api: AyanApi,
    failureCallBack: FailureCallback? = null
) {
    PaymentHelper.invoiceRegister(
        activity = activity,
        inputModel = inputModel,
        productName = productName,
        servicesPishkhan24Api = servicesPishkhan24Api,
        corePishkhan24Api = corePishkhan24Api
    ) {
        failureCallBack?.invoke(it)
    }

}