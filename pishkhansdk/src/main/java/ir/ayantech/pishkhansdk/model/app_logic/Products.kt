package ir.ayantech.pishkhansdk.model.app_logic

import ir.ayantech.pishkhansdk.model.enums.Category


object Products {

    val vehicleAuthenticityV3 = ProductItem(
        name = ProductItemDetail.INQUIRY_VEHICLE_AUTHENTICITY_V3,
        category = Category.CarServices
    )

    val transferTaxGetSettlementCertificate = ProductItem(
        name = ProductItemDetail.InquiryTransferTaxGetSettlementCertificate,
        category = Category.CarServices
    )

    val carTransferTaxV2 = ProductItem(
        name = ProductItemDetail.InquiryTransferTaxCarV2,
        category = Category.CarServices
    )

    val motorcycleTransferTaxV2 = ProductItem(
        name = ProductItemDetail.InquiryTransferTaxMotorcycleV2,
        category = Category.CarServices
    )

    val carPlateNumberHistory = ProductItem(
        name = ProductItemDetail.InquiryPlateNumberHistory,
        category = Category.CarServices,
    )

    val carTrafficFinesProduct = ProductItem(
        name = ProductItemDetail.InquiryTrafficFinesCar,
        category = Category.CarServices,
    )

    val carTrafficFinesSummaryProduct = ProductItem(
        name = ProductItemDetail.InquiryTrafficFinesCarSummary,
        category = Category.CarServices,
    )

    val plateNumbersProduct = ProductItem(
        name = ProductItemDetail.InquiryPlateNumbers,
        category = Category.CarServices,
    )

    val negativePointProduct = ProductItem(
        name = ProductItemDetail.InquiryDrivingLicenceNegativePoint,
        category = Category.CarServices,
    )

    val technicalExaminationCertificateProduct = ProductItem(
        name = ProductItemDetail.InquiryTechnicalExaminationCertificate,
        category = Category.CarServices,
    )

    val freewayTollBillsProduct = ProductItem(
        name = ProductItemDetail.InquiryFreewayTollBills,
        category = Category.CarServices,
    )

    val annualTollCarProduct = ProductItem(
        name = ProductItemDetail.InquiryAnnualTollsCar,
        category = Category.CarServices,
    )

    val trafficPlanTollCarProduct = ProductItem(
        name = ProductItemDetail.InquiryTrafficPlanTollsCar,
        category = Category.CarServices,
    )

    val motorTrafficFinesProduct = ProductItem(
        name = ProductItemDetail.InquiryTrafficFinesMotorcycle,
        category = Category.CarServices,
    )

    val motorTrafficFinesSummeryProduct = ProductItem(
        name = ProductItemDetail.InquiryTrafficFinesMotorcycleSummary,
        category = Category.CarServices,
    )

    val thirdPartyInsuranceProduct = ProductItem(
        name = ProductItemDetail.InquiryThirdPartyInsuranceCar,
        category = Category.CarServices,
    )

    val thirdPartyInsuranceStatusProduct = ProductItem(
        name = ProductItemDetail.InquiryThirdPartyInsuranceCarStatus,
        category = Category.CarServices,
    )

    val transferTaxCarProduct = ProductItem(
        name = ProductItemDetail.InquiryTransferTaxCar,
        category = Category.CarServices,
    )

    val transferTaxMotorcycleProduct = ProductItem(
        name = ProductItemDetail.InquiryTransferTaxMotorcycle,
        category = Category.CarServices,
    )

    val vehicleAuthenticityProductByBarCode = ProductItem(
        name = ProductItemDetail.InquiryVehicleAuthenticityByBarCode,
        category = Category.CarServices,
    )

    val vehicleAuthenticityProductByDocumentNumber = ProductItem(
        name = ProductItemDetail.InquiryVehicleAuthenticityByDocumentNumber,
        category = Category.CarServices,
    )


    //-----------------------------------------------------------------
    val exitBanStatusProduct = ProductItem(
        name = ProductItemDetail.InquiryExitBanStatus,
        category = Category.CitizenshipServices,
    )

    val socialSecurityInsuranceMedicalCreditProduct = ProductItem(
        name = ProductItemDetail.InquirySocialSecurityInsuranceMedicalCredit,
        category = Category.CitizenshipServices,
    )

    val socialSecurityInsuranceHistoryProduct = ProductItem(
        name = ProductItemDetail.InquirySocialSecurityInsuranceHistory,
        category = Category.CitizenshipServices,
    )

    val justiceSharesProduct = ProductItem(
        name = ProductItemDetail.InquiryJusticeSharesPortfolio,
        category = Category.CitizenshipServices,
    )

    val subventionHistoryProduct = ProductItem(
        name = ProductItemDetail.InquiryGovernmentSubventionHistory,
        category = Category.CitizenshipServices,
    )

    val socialSecurityInsuranceRetirementReceiptProduct = ProductItem(
        name = ProductItemDetail.InquirySocialSecurityInsuranceRetirementReceipt,
        category = Category.CitizenshipServices,
    )

    val socialSecurityInsuranceRetirementProduct = ProductItem(
        name = ProductItemDetail.InquirySocialSecurityInsuranceRetirement,
        category = Category.CitizenshipServices,
    )


    val governmentRetirementProduct = ProductItem(
        name = ProductItemDetail.InquiryGovernmentRetirement,
        category = Category.CitizenshipServices,
    )

    val governmentRetirementSalaryReceiptProduct = ProductItem(
        name = ProductItemDetail.InquiryGovernmentRetirementReceipt,
        category = Category.CitizenshipServices,
    )

    val socialSecurityInsuranceMedicalPrescriptionProduct = ProductItem(
        name = ProductItemDetail.InquirySocialSecurityInsuranceMedicalPrescription,
        category = Category.CitizenshipServices,
    )

    val telecomRegisteredLinesProduct = ProductItem(
        name = ProductItemDetail.InquiryTelecomRegisteredLines,
        category = Category.CitizenshipServices,

        )

    val livelihoodInformationProduct = ProductItem(
        name = ProductItemDetail.InquiryLivelihoodInformation,
        category = Category.CitizenshipServices,
    )

    val insurancePoliciesProduct = ProductItem(
        name = ProductItemDetail.InquiryInsurancePolicies,
        category = Category.CitizenshipServices,
    )


    val waterBillProduct = ProductItem(
        name = ProductItemDetail.InquiryWaterBills,
        category = Category.BillsServices,
    )

    val electricityBillProduct = ProductItem(
        name = ProductItemDetail.InquiryElectricBills,
        category = Category.BillsServices,
    )

    val gasBillByIdentifierProduct = ProductItem(
        name = ProductItemDetail.InquiryGasBillsByIdentifier,
        category = Category.BillsServices,
    )

    val gasBillByParticipateCodeProduct = ProductItem(
        name = ProductItemDetail.InquiryGasBillsByParticipateCode,
        category = Category.BillsServices,
    )

    val landlinePhoneBillProduct = ProductItem(
        name = ProductItemDetail.InquiryLandlinePhoneBills,
        category = Category.BillsServices,
    )

    val mobileProduct = ProductItem(
        name = ProductItemDetail.MOBILE,
        category = Category.BillsServices,
    )

    val mciMobileProduct = ProductItem(
        name = ProductItemDetail.InquiryMobileMciBills,
        category = Category.BillsServices,
    )

    val irancellMobileProduct = ProductItem(
        name = ProductItemDetail.InquiryMobileIrancellBills,
        category = Category.BillsServices,
    )

    val rightelMobileProduct = ProductItem(
        name = ProductItemDetail.InquiryMobileRightelBills,
        category = Category.BillsServices,
    )

    val topUpChargeProduct = ProductItem(
        name = ProductItemDetail.TopUpCharge,
        category = Category.BillsServices,
    )

    val topUpInternetPackageProduct = ProductItem(
        name = ProductItemDetail.TopUpInternetPackage,
        category = Category.BillsServices,
    )

    val propertyTollsProduct = ProductItem(
        name = ProductItemDetail.InquiryPropertyTolls,
        category = Category.BillsServices,
    )

    val taxiFaresBillProduct = ProductItem(
        name = ProductItemDetail.InquiryMunicipalityTaxiFaresBills,
        category = Category.BillsServices,
    )

    val paperBillProduct = ProductItem(
        name = ProductItemDetail.InquiryPaperBills,
        category = Category.BillsServices,
    )

    val realEstateContractProduct = ProductItem(
        name = ProductItemDetail.InquiryRealEstateContract,
        category = Category.CitizenshipServices,
    )


    val postPackageTrackingProduct = ProductItem(
        name = ProductItemDetail.InquiryPostPackageTracking,
        category = Category.PostServices,
    )

    val identificationDocumentsStatusCarProduct = ProductItem(
        name = ProductItemDetail.InquiryIdentificationDocumentsStatusCar,
        category = Category.PostServices,
    )

    val identificationDocumentsStatusMotorcycleProduct = ProductItem(
        name = ProductItemDetail.InquiryIdentificationDocumentsStatusMotorcycle,
        category = Category.PostServices,
    )

    val drivingLicenceStatusProduct = ProductItem(
        name = ProductItemDetail.InquiryDrivingLicenceStatus,
        category = Category.PostServices,
    )

    val passportStatusProduct = ProductItem(
        name = ProductItemDetail.InquiryPassportStatus,
        category = Category.PostServices,
    )

    val sayadChequeProduct = ProductItem(
        name = ProductItemDetail.InquirySayadCheque,
        category = Category.FinancialServices,
    )

    val sheba = ProductItem(
        name = ProductItemDetail.SHEBA,
        category = Category.FinancialServices,
    )

    val ibanByAccountNumberProduct = ProductItem(
        name = ProductItemDetail.InquiryIbanByAccountNumber,
        category = Category.FinancialServices,
    )

    val ibanByCardNumberProduct = ProductItem(
        name = ProductItemDetail.InquiryIbanByCardNumber,
        category = Category.FinancialServices,
    )

    val accountNumberByIbanProduct = ProductItem(
        name = ProductItemDetail.InquiryAccountNumberByIban,
        category = Category.FinancialServices,
    )

}