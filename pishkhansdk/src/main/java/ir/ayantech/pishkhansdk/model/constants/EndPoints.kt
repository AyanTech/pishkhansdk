package ir.ayantech.pishkhansdk.model.constants

object EndPoints {

    //services
    const val JusticeSharesPortfolio = "v1/JusticeSharesPortfolio"
    const val TrafficFinesCar = "v1/TrafficFinesCar"
    const val TrafficFinesImages = "v1/TrafficFinesImages"
    const val TrafficFinesCarSummary = "v1/TrafficFinesCarNoDetails"
    const val TrafficFinesMotorcycle = "v1/TrafficFinesMotorcycle"
    const val TrafficFinesMotorcycleSummary = "v1/TrafficFinesMotorcycleNoDetails"
    const val TopUpOperators = "v1/TopUpOperators"
    const val TopUpProducts = "v1/TopUpProducts"
    const val VehiclePlateNumbers = "v1/VehiclePlateNumbers"
    const val DrivingLicenseNegativePoint = "v1/DrivingLicenseNegativePoint"
    const val ExitBanStatus = "v1/ExitBanStatus"
    const val PassportStatus = "v1/PassportStatus"
    const val DrivingLicenseStatus = "v1/DrivingLicenseStatus"
    const val IdentificationDocumentsStatusCar = "v1/IdentificationDocumentsStatusCar"
    const val TechnicalExaminationCertificate = "v1/TechnicalExaminationCertificate"
    const val SocialSecurityInsuranceHistory = "v1/SocialSecurityInsuranceHistory"
    const val SocialSecurityInsuranceMedicalCredit = "v2/SocialSecurityInsuranceMedicalCredit"
    const val SocialSecurityInsuranceRetirement = "v1/SocialSecurityInsuranceRetirement"
    const val SocialSecurityInsuranceRetirementReceipt =
        "v1/SocialSecurityInsuranceRetirementReceipt"
    const val GovernmentRetirement = "v1/GovernmentRetirement"
    const val GovernmentRetirementReceipt = "v1/GovernmentRetirementReceipt"
    const val WaterBills = "v1/WaterBills"
    const val WaterBillsExport = "v1/WaterBillsExport"
    const val ElectricBills = "v1/ElectricBills"
    const val ElectricBillsExport = "v1/ElectricBillsExport"
    const val GasBillsBillIdentifier = "v1/GasBills" // 	By Bill Identifier
    const val GasBillsParticipateCode = "v2/GasBills" // By Participate Code
    const val LandLinePhoneBills = "v1/LandLinePhoneBills"
    const val CellPhoneBills = "v1/CellPhoneBills"
    const val LandLinePhoneBillsDetails = "v2/LandLinePhoneBillsDetails"
    const val LandLinePhoneBillsExport = "v2/LandLinePhoneBillsExport"
    const val CellPhoneBillsDetails = "v1/CellPhoneBillsDetails"
    const val GovernmentSubventionHistory = "v2/GovernmentSubventionHistory"
    const val MunicipalityPropertyTollBills = "v1/MunicipalityPropertyTollBills"
    const val MunicipalityPropertyTollBillsBarcodeInfo =
        "v1/MunicipalityPropertyTollBillsBarcodeInfo"
    const val TopUpProductPurchase = "v1/TopUpProductPurchase"
    const val MunicipalityTaxiFaresBills = "v1/MunicipalityTaxiFaresBills"
    const val MunicipalityTaxiFaresBillsBarcodeInfo = "v1/MunicipalityTaxiFaresBillsBarcodeInfo"
    const val FreewayTollBills = "v2/FreewayTollBills"
    const val MunicipalityCarAnnualTollBills = "v1/MunicipalityCarAnnualTollBills"
    const val MunicipalityCarTollBills = "v1/MunicipalityCarTollBills"
    const val v1BankIbanInfo = "v1/BankIbanInfo"
    const val v2BankIbanInfo = "v2/BankIbanInfo" // By Account Number
    const val v3BankIbanInfo = "v3/BankIbanInfo" // By Card Number
    const val BankIbanInfoPrerequisites = "v1/BankIbanInfoPrerequisites"
    const val TransferTaxCar = "v1/TransferTaxCar"
    const val TransferTaxMotorcycle = "v1/TransferTaxMotorcycle"
    const val PostPackagesStatus = "v1/PostPackagesStatus"
    const val BankChequeStatusSayad = "v1/BankChequeStatusSayad"

    const val VehicleThirdPartyInsurance = "v1/VehicleThirdPartyInsurance"
    const val VehicleThirdPartyInsuranceStatus = "v1/VehicleThirdPartyInsuranceStatus"

    const val IdentificationDocumentsStatusMotorcycle = "v1/IdentificationDocumentsStatusMotorcycle"
    const val socialSecurityInsuranceMedicalPrescription =
        "v1/SocialSecurityInsuranceMedicalPrescription"
    const val socialSecurityInsuranceMedicalPrescriptionDetails =
        "v1/SocialSecurityInsuranceMedicalPrescriptionDetails"
    const val RealEstateContract = "v1/RealEstateContract"
    @Deprecated(message = "Use version 3 of this API. New version: $VEHICLE_AUTHENTICITY_V3")
    const val VehicleAuthenticity = "v2/VehicleAuthenticity"
    const val CarPlateNumberHistory = "v1/PlateNumberHistory"
    const val TelecomRegisteredLines = "v1/TelecomRegisteredLines"
    const val LivelihoodInformation = "v1/LivelihoodInformation"
    const val InsurancePolicies = "v1/InsurancePolicies"

    const val TRANSFER_TAX_CAR_V2 = "v2/TransferTaxCar"
    const val TRANSFER_TAX_MOTORCYCLE_V2 = "v2/TransferTaxMotorcycle"
    const val TRANSFER_TAX_GET_SETTLEMENT_CERTIFICATE = "v1/TransferTaxGetSettlementCertificate"
    const val CAR_ANNUAL_TAX_BILLS = "v1/CarAnnualTaxBills"
    const val TRAFFIC_FINES_IMAGES_V2 = "v2/TrafficFinesImages"
    const val VEHICLE_AUTHENTICITY_V3 = "v3/VehicleAuthenticity"
    const val CAR_ANNUAL_TAX_GET_SETTLEMENT_CERTIFICATE = "v1/CarAnnualTaxGetSettlementCertificate"
    const val FREEWAY_TOLL_BILLS_DETAILED = "v1/FreewayTollBillsDetailed"

    //core
    const val InvoicePayment = "v1/InvoicePayment"
    const val InvoiceInfo = "v1/InvoiceInfo"
    const val DeviceRegister = "v1/DeviceRegister"
    const val InvoiceRegister = "v1/InvoiceRegister"
    const val UserServiceQueries = "v1/UserServiceQueries"
    const val UserServiceQueryBookmark = "v1/UserServiceQueryBookmark"
    const val UserServiceQueryDelete = "v1/UserServiceQueryDelete"
    const val UserServiceQueryNote = "v1/UserServiceQueryNote"
    const val UserSessionUpdateInfo = "v1/UserSessionUpdateInfo"
    const val UserTransactions = "v1/UserTransactions"
    const val LoginByOTP = "v1/LoginByOTP"
    const val BillsInfo = "v1/BillsInfo"


    const val UserInvoicePaymentSummary = "v1/UserInvoicePaymentSummary"
    const val USER_BILLS_PAYMENT_SUMMARY = "v1/UserBillsPaymentSummary"
    const val GET_USER_DATA = "v1/GetUserData"
    const val ADD_USER_DATA = "v1/AddUserData"
    const val WALLET_PAYMENT = "v1/WalletPayment"
    const val CNPG_REQUEST_OTP = "v1/CnpgRequestOtp"
    const val CNPG_PAYMENT = "v1/CnpgPayment"
    const val CNPG_GET_CARD_LIST = "v1/CnpgGetCardList"
    const val CNPG_REMOVE_CARD = "v1/CnpgRemoveCard"
    const val SHAPARAK_BANK_LIST_SERVICE = "v1/ShaparakBankListService"
    const val USER_PUBLIC_KEY = "v1/UserPublicKey"
    const val USER_WALLET_CHARGE = "v1/UserWalletCharge"
    const val USER_WALLET_CHARGE_CHANNELS = "v1/UserWalletChargeChannels"
    const val BILLS_PAYMENT_CHANNELS = "v1/BillsPaymentChannels"
    const val BILLS_PAYMENT = "v1/BillsPayment"
    const val USER_WALLET_BALANCE = "v1/UserWalletBalance"
    const val USER_WALLETS = "v1/UserWallets"

}