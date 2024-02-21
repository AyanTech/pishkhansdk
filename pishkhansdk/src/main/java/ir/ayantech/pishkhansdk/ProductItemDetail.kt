package ir.ayantech.pishkhansdk

object ProductItemDetail {
    const val InquiryWaterBills = "v1_InquiryWaterBills"
    const val InquiryElectricBills = "v1_InquiryElectricBills"

    const val InquiryGasBillsByIdentifier = "v1_InquiryGasBills"
    const val InquiryGasBillsByParticipateCode = "v2_InquiryGasBills"
    const val InquiryLandlinePhoneBills = "v1_InquiryLandlinePhoneBills"
    const val InquiryMobileMciBills = "InquiryMobileMciBills"
    const val InquiryMobileIrancellBills = "InquiryMobileIrancellBills"
    const val InquiryMobileRightelBills = "InquiryMobileRightelBills"
    const val TopUpCharge = "v1_TopUpCharge"
    const val TopUpInternetPackage = "v1_TopUpInternetPackage"
    const val InquiryPropertyTolls = "v1_InquiryMunicipalityPropertyTollBills"
    const val MOBILE = "v1_InquiryCellPhoneBills"
    const val InquiryRealEstateContract = "v1_InquiryRealEstateContract"


    const val CAR_TRAFFIC_FINES = "v1_InquiryTrafficFinesCar"
    const val InquiryPlateNumbers = "v1_InquiryVehiclePlateNumbers"
    const val InquiryDrivingLicenceNegativePoint = "v1_InquiryDrivingLicenseNegativePoint"
    const val InquiryTechnicalExaminationCertificate = "v1_InquiryTechnicalExaminationCertificate"
    const val InquiryFreewayTollBills = "v1_InquiryFreewayTollBills"
    const val InquiryAnnualTollsCar = "v1_InquiryMunicipalityCarAnnualTollBills"
    const val InquiryTrafficPlanTollsCar = "v1_InquiryMunicipalityCarTollBills"
    const val MOTOR_TRAFFIC_FINES = "v1_InquiryTrafficFinesMotorcycle"
    const val InquiryThirdPartyInsuranceCar = "v1_InquiryVehicleThirdPartyInsurance"
    const val InquiryThirdPartyInsuranceCarStatus = "v1_InquiryVehicleThirdPartyInsuranceStatus"
    const val InquiryTrafficFinesCar = "v1_InquiryTrafficFinesCar"
    const val InquiryTrafficFinesCarSummary = "v1_InquiryTrafficFinesCarSummary"
    const val InquiryTrafficFinesMotorcycle = "v1_InquiryTrafficFinesMotorcycle"
    const val InquiryTrafficFinesMotorcycleSummary = "v1_InquiryTrafficFinesMotorcycleSummary"
    const val InquiryMunicipalityTaxiFaresBills = "v1_InquiryMunicipalityTaxiFaresBills"
    const val InquiryTransferTaxCar = "v1_InquiryTransferTaxCar"
    const val InquiryTransferTaxMotorcycle = "v1_InquiryTransferTaxMotorcycle"
    const val InquiryVehicleAuthenticityByBarCode = "v1_InquiryVehicleAuthenticity"
    const val InquiryVehicleAuthenticityByDocumentNumber = "v2_InquiryVehicleAuthenticity"

    const val InquiryPostPackageTracking = "v1_InquiryPostPackagesStatus"
    const val InquiryIdentificationDocumentsStatusCar = "v1_InquiryIdentificationDocumentsStatusCar"
    const val InquiryDrivingLicenceStatus = "v1_InquiryDrivingLicenseStatus"
    const val InquiryPassportStatus = "v1_InquiryPassportStatus"
    const val InquiryIdentificationDocumentsStatusMotorcycle =
        "v1_InquiryIdentificationDocumentsStatusMotorcycle"

    const val InquiryCryptoCurrencyPrice = "v1_ExchangeCrypto"
    const val InquiryExchangeCurrencyPrice = "v1_ExchangeCurrency"
    const val InquirySayadCheque = "v1_InquiryBankChequeStatusSayad"
    const val SHEBA = "v1_InquiryBankIbanInfo"
    const val InquiryIbanByAccountNumber = "v2_InquiryBankIbanInfo"
    const val InquiryIbanByCardNumber = "v3_InquiryBankIbanInfo"
    const val InquiryAccountNumberByIban = "v1_InquiryBankIbanInfo"
    const val InquiryExchangeGold = "v1_ExchangeGold"

    const val InquiryExitBanStatus = "v1_InquiryExitBanStatus"
    const val InquirySocialSecurityInsuranceMedicalCredit =
        "v2_InquirySocialSecurityInsuranceMedicalCredit"
    const val InquirySocialSecurityInsuranceHistory = "v1_InquirySocialSecurityInsuranceHistory"
    const val InquiryJusticeSharesPortfolio = "v1_InquiryJusticeSharesPortfolio"
    const val InquiryGovernmentSubventionHistory = "v2_InquiryGovernmentSubventionHistory"
    const val SALARY_RECEIPT = "SALARY_RECEIPT"
    const val RETIREMENT_RECEIPT = "RETIREMENT_RECEIPT"
    const val InquirySocialSecurityInsuranceRetirementReceipt =
        "v1_InquirySocialSecurityInsuranceRetirementReceipt"
    const val InquiryGovernmentRetirementReceipt = "v1_InquiryGovernmentRetirementReceipt"
    const val InquirySocialSecurityInsuranceRetirement =
        "v1_InquirySocialSecurityInsuranceRetirement"
    const val InquiryGovernmentRetirement = "v1_InquiryGovernmentRetirement"
    const val InquirySocialSecurityInsuranceMedicalPrescription =
        "v1_InquirySocialSecurityInsuranceMedicalPrescription"
    const val InquiryTelecomRegisteredLines = "v1_InquiryTelecomRegisteredLines"
    const val InquiryLivelihoodInformation = "v1_InquiryLivelihoodInformation"
    const val InquiryInsurancePolicies = "v1_InquiryInsurancePolicies"
    const val THIRD_PARTY_INSURANCE = "THIRD_PARTY_INSURANCE"
    const val InquiryPaperBills = "v1_paperBills"

}
fun String.getProductShowName() = when (this) {
    ProductItemDetail.InquiryWaterBills -> "قبض آب"
    ProductItemDetail.InquiryElectricBills -> "قبض برق"
    ProductItemDetail.InquiryGasBillsByIdentifier,
    ProductItemDetail.InquiryGasBillsByParticipateCode -> "قبض گاز"

    ProductItemDetail.InquiryLandlinePhoneBills -> "قبض تلفن ثابت"
    ProductItemDetail.MOBILE -> "قبض تلفن همراه"
    ProductItemDetail.InquiryMobileMciBills -> "قبض همراه اول"
    ProductItemDetail.InquiryMobileIrancellBills -> "قبض ایرانسل"
    ProductItemDetail.InquiryMobileRightelBills -> "قبض رایتل"
    ProductItemDetail.TopUpCharge -> "خرید شارژ"
    ProductItemDetail.TopUpInternetPackage -> "خرید بسته اینترنت"
    ProductItemDetail.InquiryPropertyTolls -> "عوارض ملک"
    ProductItemDetail.InquiryRealEstateContract -> "قرارداد اجاره نامه"

    ProductItemDetail.CAR_TRAFFIC_FINES -> "خلافی خودرو"
    ProductItemDetail.InquiryPlateNumbers -> "پلاک\u200Cهای فعال"
    ProductItemDetail.InquiryDrivingLicenceNegativePoint -> "نمره منفی"
    ProductItemDetail.InquiryTechnicalExaminationCertificate -> "معاینه فنی"
    ProductItemDetail.InquiryFreewayTollBills -> "عوارض آزادراهی"
    ProductItemDetail.InquiryAnnualTollsCar -> "عوارض سالیانه"
    ProductItemDetail.InquiryTrafficPlanTollsCar -> "طرح ترافیک"
    ProductItemDetail.MOTOR_TRAFFIC_FINES -> "خلافی موتور"
    ProductItemDetail.THIRD_PARTY_INSURANCE,
    ProductItemDetail.InquiryThirdPartyInsuranceCar -> "بیمه شخص ثالث"
    ProductItemDetail.InquiryThirdPartyInsuranceCarStatus -> "بیمه شخص ثالث بدون جزئیات"
    ProductItemDetail.InquiryTrafficFinesCarSummary -> "خلافی تجمیعی خودرو"
    ProductItemDetail.InquiryTrafficFinesMotorcycleSummary -> "خلافی تجمیعی موتور"
    ProductItemDetail.InquiryMunicipalityTaxiFaresBills -> "کرایه تاکسی"
    ProductItemDetail.InquiryTransferTaxCar -> "مالیات انتقال خودرو"
    ProductItemDetail.InquiryTransferTaxMotorcycle -> "مالیات انتقال موتور"
    ProductItemDetail.InquiryVehicleAuthenticityByBarCode,
    ProductItemDetail.InquiryVehicleAuthenticityByDocumentNumber -> "سوابق خودرو"

    ProductItemDetail.InquiryPostPackageTracking -> "پیگیری مرسولات پستی"
    ProductItemDetail.InquiryIdentificationDocumentsStatusCar -> "پیگیری مدارک خودرو"
    ProductItemDetail.InquiryDrivingLicenceStatus -> "پیگیری گواهینامه"
    ProductItemDetail.InquiryPassportStatus -> "پیگیری گذرنامه"
    ProductItemDetail.InquiryIdentificationDocumentsStatusMotorcycle -> "پیگیری مدارک موتور"

    ProductItemDetail.InquiryCryptoCurrencyPrice -> " ارز دیجیتال"
    ProductItemDetail.InquirySayadCheque -> "چک صیادی"
    ProductItemDetail.InquiryIbanByAccountNumber -> "شماره حساب"
    ProductItemDetail.InquiryIbanByCardNumber -> "شماره کارت"
    ProductItemDetail.InquiryAccountNumberByIban -> "تبدیل شبا"
    ProductItemDetail.InquiryExchangeCurrencyPrice -> "ارزهای جهانی"
    ProductItemDetail.InquiryExchangeGold -> "طلا و سکه"

    ProductItemDetail.InquiryExitBanStatus -> "ممنوع الخروجی"
    ProductItemDetail.InquirySocialSecurityInsuranceMedicalCredit -> "اعتبار دفترچه بیمه"
    ProductItemDetail.InquirySocialSecurityInsuranceHistory -> "سوابق بیمه"
    ProductItemDetail.InquiryJusticeSharesPortfolio -> "سهام عدالت"
    ProductItemDetail.InquiryGovernmentSubventionHistory -> "یارانه نقدی"
    ProductItemDetail.SALARY_RECEIPT -> "فیش حقوقی"
    ProductItemDetail.RETIREMENT_RECEIPT -> "حکم بازنشستگی"
    ProductItemDetail.InquirySocialSecurityInsuranceRetirementReceipt -> "فیش حقوقی تامین اجتماعی"
    ProductItemDetail.InquiryGovernmentRetirementReceipt -> "فیش حقوقی کشوری"
    ProductItemDetail.InquirySocialSecurityInsuranceRetirement -> "حکم بازنشستگی تامین اجتماعی"
    ProductItemDetail.InquiryGovernmentRetirement -> "حکم بازنشستگی کشوری"
    ProductItemDetail.InquirySocialSecurityInsuranceMedicalPrescription -> "نسخه الکترونیک"
    ProductItemDetail.InquiryTelecomRegisteredLines -> "سیم کارت\u200Cهای به\u200Cنام"
    ProductItemDetail.InquiryLivelihoodInformation -> "معیشتی"
    ProductItemDetail.InquiryInsurancePolicies -> "بیمه نامه"

    ProductItemDetail.InquiryPaperBills -> "قبض کاغذی"

    else -> "محصول جدید!"
}

