package ir.ayantech.pishkhansdk.model.app_logic

import ir.ayantech.pishkhansdk.model.enums.Category

abstract class PishkhanItem(
    val name: String,
    val showName: String,
    val category: Category,
)

class ProductItem(
    name: String,
    category: Category,
) : PishkhanItem(
    name,
    name.getProductShowName(),
    category,
)


fun String.resolveNameToPishkhanItem(): PishkhanItem? {
    return availablePishkhanItems.firstOrNull { it.name == this }
}

private val availablePishkhanItems = listOf(
    Products.carTrafficFinesProduct,
    Products.carTrafficFinesSummaryProduct,
    Products.plateNumbersProduct,
    Products.negativePointProduct,
    Products.technicalExaminationCertificateProduct,
    Products.freewayTollBillsProduct,
    Products.annualTollCarProduct,
    Products.trafficPlanTollCarProduct,
    Products.motorTrafficFinesProduct,
    Products.motorTrafficFinesSummeryProduct,
    Products.thirdPartyInsuranceProduct,
    Products.thirdPartyInsuranceStatusProduct,
    Products.transferTaxCarProduct,
    Products.transferTaxMotorcycleProduct,
    Products.exitBanStatusProduct,
    Products.socialSecurityInsuranceMedicalCreditProduct,
    Products.socialSecurityInsuranceHistoryProduct,
    Products.justiceSharesProduct,
    Products.subventionHistoryProduct,
    Products.socialSecurityInsuranceRetirementReceiptProduct,
    Products.governmentRetirementSalaryReceiptProduct,
    Products.socialSecurityInsuranceRetirementProduct,
    Products.governmentRetirementProduct,
    Products.waterBillProduct,
    Products.electricityBillProduct,
    Products.gasBillByIdentifierProduct,
    Products.gasBillByParticipateCodeProduct,
    Products.landlinePhoneBillProduct,
    Products.mobileProduct,
    Products.mciMobileProduct,
    Products.irancellMobileProduct,
    Products.rightelMobileProduct,
    Products.topUpChargeProduct,
    Products.topUpInternetPackageProduct,
    Products.propertyTollsProduct,
    Products.paperBillProduct,
    Products.postPackageTrackingProduct,
    Products.identificationDocumentsStatusCarProduct,
    Products.drivingLicenceStatusProduct,
    Products.passportStatusProduct,
    Products.sayadChequeProduct,
    Products.sheba,
    Products.ibanByAccountNumberProduct,
    Products.ibanByCardNumberProduct,
    Products.accountNumberByIbanProduct,
    Products.taxiFaresBillProduct,
    Products.socialSecurityInsuranceMedicalPrescriptionProduct,
    Products.telecomRegisteredLinesProduct,
    Products.livelihoodInformationProduct,
    Products.insurancePoliciesProduct,
    Products.identificationDocumentsStatusMotorcycleProduct,
    Products.realEstateContractProduct,
    Products.vehicleAuthenticityProductByBarCode,
    Products.vehicleAuthenticityProductByDocumentNumber,
    Products.carPlateNumberHistory,
)