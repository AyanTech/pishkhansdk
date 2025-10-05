package ir.ayantech.pishkhansdk.model.app_logic

import ir.ayantech.pishkhansdk.R

object ProductItemDetail {
    //Service names
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
    const val InquiryFreewayTollBills = "v2_InquiryFreewayTollBills"
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
    const val InquiryVehicleAuthenticityByBarCode = "v2_InquiryVehicleAuthenticity"
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

    const val InquiryPlateNumberHistory = "v1_InquiryPlateNumberHistory"

    const val InquiryTransferTaxCarV2 = "v2_InquiryTransferTaxCar"
    const val InquiryTransferTaxMotorcycleV2 = "v2_InquiryTransferTaxMotorcycle"
    const val InquiryTransferTaxGetSettlementCertificate = "v1_InquiryTransferTaxGetSettlementCertificate"

    const val INQUIRY_VEHICLE_AUTHENTICITY_V3 = "v3_InquiryVehicleAuthenticity"
    const val INQUIRY_FREEWAY_TOLL_BILLS_DETAILED = "v1_InquiryFreewayTollBillsDetailed"
    const val INQUIRY_CAR_ANNUAL_TAX_GET_SETTLEMENT_CERTIFICATE = "v1_InquiryCarAnnualTaxGetSettlementCertificate"
    const val INQUIRY_CAR_ANNUAL_TAX_BILLS = "v1_InquiryCarAnnualTaxBills"
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

    ProductItemDetail.CAR_TRAFFIC_FINES -> "خلافی با جزئیات  خودرو"
    ProductItemDetail.InquiryPlateNumbers -> "پلاک\u200Cهای فعال"
    ProductItemDetail.InquiryDrivingLicenceNegativePoint -> "نمره منفی"
    ProductItemDetail.InquiryTechnicalExaminationCertificate -> "معاینه فنی"
    ProductItemDetail.InquiryFreewayTollBills -> "عوارض آزادراهی"
    ProductItemDetail.InquiryAnnualTollsCar -> "عوارض سالیانه"
    ProductItemDetail.InquiryTrafficPlanTollsCar -> "طرح ترافیک"
    ProductItemDetail.MOTOR_TRAFFIC_FINES -> "خلافی با جزئیات  موتور"
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

    ProductItemDetail.InquiryPlateNumberHistory -> "تاریخچه پلاک خودرو"

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

fun String.getCardHistoryIcon() = when (this) {
    ProductItemDetail.InquiryTrafficFinesCar,
    ProductItemDetail.InquiryTrafficFinesCarSummary -> R.drawable.ic_car_fines_history_card

    ProductItemDetail.InquiryFreewayTollBills -> R.drawable.ic_freeway_toll_history_card
    ProductItemDetail.InquiryTrafficPlanTollsCar -> R.drawable.ic_traffic_plan_history_card
    ProductItemDetail.InquiryAnnualTollsCar -> R.drawable.ic_annual_toll_history_card

    ProductItemDetail.InquiryTrafficFinesMotorcycle,
    ProductItemDetail.InquiryTrafficFinesMotorcycleSummary -> R.drawable.ic_motor_fines_history_card

    ProductItemDetail.InquiryTransferTaxCar -> R.drawable.ic_transfer_tax_car_history_card
    ProductItemDetail.InquiryTransferTaxMotorcycle -> R.drawable.ic_transfer_tax_motor_cycle_history_card
    ProductItemDetail.InquiryPlateNumbers -> R.drawable.ic_active_plate_history_card
    ProductItemDetail.InquiryDrivingLicenceNegativePoint -> R.drawable.ic_negative_point_history_card
    ProductItemDetail.InquiryDrivingLicenceStatus -> R.drawable.ic_driving_licence_history_card
    ProductItemDetail.InquiryIdentificationDocumentsStatusCar -> R.drawable.ic_identification_document_status_car_history_card
    ProductItemDetail.InquiryTechnicalExaminationCertificate -> R.drawable.ic_technical_examination_history_card
    ProductItemDetail.InquiryPlateNumberHistory -> R.drawable.ic_plate_history_history_card
    ProductItemDetail.InquiryVehicleAuthenticityByBarCode-> R.drawable.ic_vehicle_authenticity

    ProductItemDetail.InquiryThirdPartyInsuranceCar,
    ProductItemDetail.InquiryThirdPartyInsuranceCarStatus -> R.drawable.ic_third_party_insurance_history_card

    else -> R.drawable.ic_payments
}