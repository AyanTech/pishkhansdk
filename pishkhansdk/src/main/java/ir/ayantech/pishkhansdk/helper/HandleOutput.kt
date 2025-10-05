package ir.ayantech.pishkhansdk.helper


import ir.ayantech.networking.simpleCallBankChequeStatusSayad
import ir.ayantech.networking.simpleCallCarPlateNumberHistory
import ir.ayantech.networking.simpleCallCellPhoneBills
import ir.ayantech.networking.simpleCallDrivingLicenseNegativePoint
import ir.ayantech.networking.simpleCallDrivingLicenseStatus
import ir.ayantech.networking.simpleCallFreewayTollBills
import ir.ayantech.networking.simpleCallFreewayTollBillsDetailed
import ir.ayantech.networking.simpleCallIdentificationDocumentsStatusCar
import ir.ayantech.networking.simpleCallJusticeSharesPortfolio
import ir.ayantech.networking.simpleCallLandLinePhoneBills
import ir.ayantech.networking.simpleCallMunicipalityCarAnnualTollBills
import ir.ayantech.networking.simpleCallMunicipalityCarTollBills
import ir.ayantech.networking.simpleCallPassportStatus
import ir.ayantech.networking.simpleCallPostPackagesStatus
import ir.ayantech.networking.simpleCallServiceBills
import ir.ayantech.networking.simpleCallSubventionHistory
import ir.ayantech.networking.simpleCallTrafficFinesCar
import ir.ayantech.networking.simpleCallTrafficFinesCarSummary
import ir.ayantech.networking.simpleCallTransferTaxCar
import ir.ayantech.networking.simpleCallTransferTaxCarV2
import ir.ayantech.networking.simpleCallTransferTaxGetSettlementCertificate
import ir.ayantech.networking.simpleCallTransferTaxMotorcycle
import ir.ayantech.networking.simpleCallTransferTaxMotorcycleV2
import ir.ayantech.networking.simpleCallVehicleAuthenticity
import ir.ayantech.networking.simpleCallVehicleAuthenticityV3
import ir.ayantech.networking.simpleCallVehiclePlateNumbers
import ir.ayantech.networking.simpleCallVehicleThirdPartyInsurance
import ir.ayantech.networking.simpleCallVehicleThirdPartyInsuranceStatus
import ir.ayantech.pishkhansdk.model.api.BankChequeStatusSayad
import ir.ayantech.pishkhansdk.model.api.CarPlateNumberHistory
import ir.ayantech.pishkhansdk.model.api.CellPhoneBills
import ir.ayantech.pishkhansdk.model.api.DrivingLicenseNegativePoint
import ir.ayantech.pishkhansdk.model.api.DrivingLicenseStatus
import ir.ayantech.pishkhansdk.model.api.FreewayTollBills
import ir.ayantech.pishkhansdk.model.api.FreewayTollBillsDetailed
import ir.ayantech.pishkhansdk.model.api.IdentificationDocumentsStatusCar
import ir.ayantech.pishkhansdk.model.api.InvoiceInfo
import ir.ayantech.pishkhansdk.model.api.JusticeSharesPortfolio
import ir.ayantech.pishkhansdk.model.api.LandLinePhoneBills
import ir.ayantech.pishkhansdk.model.api.MunicipalityCarAnnualTollBills
import ir.ayantech.pishkhansdk.model.api.MunicipalityCarTollBills
import ir.ayantech.pishkhansdk.model.api.PassportStatus
import ir.ayantech.pishkhansdk.model.api.PostPackagesStatus
import ir.ayantech.pishkhansdk.model.api.ServiceBills
import ir.ayantech.pishkhansdk.model.api.SubventionHistory
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCar
import ir.ayantech.pishkhansdk.model.api.TrafficFinesCarSummary
import ir.ayantech.pishkhansdk.model.api.TransferTaxCar
import ir.ayantech.pishkhansdk.model.api.TransferTaxCarV2
import ir.ayantech.pishkhansdk.model.api.TransferTaxGetSettlementCertificate
import ir.ayantech.pishkhansdk.model.api.TransferTaxMotorcycle
import ir.ayantech.pishkhansdk.model.api.TransferTaxMotorcycleV2
import ir.ayantech.pishkhansdk.model.api.V1BankIbanInfo
import ir.ayantech.pishkhansdk.model.api.V2BankIbanInfo
import ir.ayantech.pishkhansdk.model.api.V3BankIbanInfo
import ir.ayantech.pishkhansdk.model.api.VehicleAuthenticity
import ir.ayantech.pishkhansdk.model.api.VehicleAuthenticityV3
import ir.ayantech.pishkhansdk.model.api.VehiclePlateNumbers
import ir.ayantech.pishkhansdk.model.api.VehicleThirdPartyInsurance
import ir.ayantech.pishkhansdk.model.api.VehicleThirdPartyInsuranceStatus
import ir.ayantech.pishkhansdk.model.app_logic.BaseInputModel
import ir.ayantech.pishkhansdk.model.app_logic.BaseResultModel
import ir.ayantech.pishkhansdk.model.app_logic.Products
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.model.constants.Parameter
import ir.ayantech.whygoogle.helper.isNull

object HandleOutput {

    fun handleOutputResult(
        invoiceInfoOutput: InvoiceInfo.Output,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        when (invoiceInfoOutput.Invoice.Service.Type.Name) {

            Products.freewayTollBillsDetailed.name -> {
                callFreewayTollBillsDetailedInquiry(
                    input = FreewayTollBillsDetailed.Input(
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey,
                        PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    )
                )
            }

            Products.vehicleAuthenticityV3.name -> {
                callVehicleAuthenticityInquiryV3(
                    input = VehicleAuthenticityV3.Input(
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey,
                        Identifier = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Identifier }.Value,
                        IdentifierType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.IdentifierType }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                        BirthDate = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.BirthDate }.Value,
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.transferTaxGetSettlementCertificate.name -> {
                callTransferTaxGetSettlementCertificate(
                    input = TransferTaxGetSettlementCertificate.Input(
                        BillUniqueID = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.BillUniqueID }.Value,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.carTransferTaxV2.name -> {
                callCarTransferTaxV2Inquiry(
                    input = TransferTaxCarV2.Input(
                        DateOfBirth = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.DateOfBirth }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey,
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.motorcycleTransferTaxV2.name -> {
                callMotorcycleTransferTaxV2Inquiry(
                    input = TransferTaxMotorcycleV2.Input(
                        DateOfBirth = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.DateOfBirth }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey,
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.carPlateNumberHistory.name -> {
                handleCarPlateNumberHistory(
                    input = CarPlateNumberHistory.Input(
                        MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
//                        OTPCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.OTPCode }.Value,
                        OTPCode = "",
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey,
                        PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.vehicleAuthenticityProductByDocumentNumber.name,
            Products.vehicleAuthenticityProductByBarCode.name -> {
                handleInquiryVehicleAuthenticity(
                    input = VehicleAuthenticity.Input(
                        Identifier = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Identifier }.Value,
                        IdentifierType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.IdentifierType }.Value,
                        NationalCodeOwner = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCodeOwner }.Value,
                        NationalCodeBuyer = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCodeBuyer }.Value,
                        OTPCode = null,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.transferTaxCarProduct.name -> {
                handleInquiryTransferTaxCar(
                    input = TransferTaxCar.Input(
                        DateOfBirth = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.DateOfBirth }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                        OTPCode = null,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.transferTaxMotorcycleProduct.name -> {
                handleInquiryTransferTaxMotorCycle(
                    input = TransferTaxMotorcycle.Input(
                        DateOfBirth = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.DateOfBirth }.Value,
                        NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                        PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                        OTPCode = null,
                        PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                    )
                ) {
                    handleResultCallback?.invoke(it)
                }
            }

            Products.justiceSharesProduct.name -> {
                handleJusticeSharesPortfolioOutput(input = JusticeSharesPortfolio.Input(
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.subventionHistoryProduct.name -> {
                handleSubventionHistoryOutput(input = SubventionHistory.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.carTrafficFinesProduct.name -> {
                handleTrafficFinesCarOutput(input = TrafficFinesCar.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesCar, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.carTrafficFinesSummaryProduct.name -> {
                handleTrafficFinesCarSummaryOutput(input = TrafficFinesCarSummary.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesCarSummary, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.motorTrafficFinesProduct.name -> {
                handleTrafficFinesCarOutput(input = TrafficFinesCar.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesMotorcycle, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.motorTrafficFinesSummeryProduct.name -> {
                handleTrafficFinesCarSummaryOutput(input = TrafficFinesCarSummary.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.TrafficFinesMotorcycleSummary, handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.ibanByCardNumberProduct.name -> {
                handleIbanByCardNumberOutput(input = V3BankIbanInfo.Input(
                    AccountType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountType }.Value,
                    CardNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.CardNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.ibanByAccountNumberProduct.name -> {
                handleIbanByAccountNumberOutput(input = V2BankIbanInfo.Input(
                    AccountType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountType }.Value,
                    AccountNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountNumber }.Value,
                    Bank = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Bank }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.accountNumberByIbanProduct.name -> {
                handleAccountNumberByIbanOutput(input = V1BankIbanInfo.Input(
                    AccountType = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.AccountType }.Value,
                    Iban = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Iban }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.postPackageTrackingProduct.name -> {
                handlePostPackageTrackingOutput(input = PostPackagesStatus.Input(
                    TrackingCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.TrackingCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.sayadChequeProduct.name -> {
                handleBankChequeStatusSayadOutput(input = BankChequeStatusSayad.Input(
                    ChequeNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.ChequeNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.freewayTollBillsProduct.name -> {
                handleFreewayTollBillsOutput(input = FreewayTollBills.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.trafficPlanTollCarProduct.name -> {
                handleTrafficPlanTollCarOutput(input = MunicipalityCarTollBills.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.annualTollCarProduct.name -> {
                handleAnnualTollCarOutput(input = MunicipalityCarAnnualTollBills.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    EngineNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.EngineNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    TaxGroup = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.TaxGroup }.Value,
                    VIN = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.VIN }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.plateNumbersProduct.name -> {
                handlePlateNumbersOutput(input = VehiclePlateNumbers.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.negativePointProduct.name -> {
                handleNegativePointOutput(input = DrivingLicenseNegativePoint.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    LicenseNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.LicenseNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.drivingLicenceStatusProduct.name -> {
                handleDrivingLicenceStatusOutput(input = DrivingLicenseStatus.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.identificationDocumentsStatusCarProduct.name -> {
                handleIdentificationDocumentsStatusCarOutput(input = IdentificationDocumentsStatusCar.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.thirdPartyInsuranceProduct.name -> {
                handleThirdPartyInsuranceOutput(input = VehicleThirdPartyInsurance.Input(
                    InsuranceUniqueCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.InsuranceUniqueCode }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.thirdPartyInsuranceStatusProduct.name -> {
                handleThirdPartyInsuranceStatusOutput(input = VehicleThirdPartyInsuranceStatus.Input(
                    PlateNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.PlateNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.passportStatusProduct.name -> {
                handlePassportStatusOutput(input = PassportStatus.Input(
                    MobileNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.MobileNumber }.Value,
                    NationalCode = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.NationalCode }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), handleResultCallback = {
                    handleResultCallback?.invoke(it)
                })
            }

            Products.waterBillProduct.name -> {
                handleServiceBillsOutput(input = ServiceBills.Input(
                    Identifier = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Identifier }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.WaterBills,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    })
            }

            Products.electricityBillProduct.name -> {
                handleServiceBillsOutput(input = ServiceBills.Input(
                    Identifier = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Identifier }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.ElectricBills,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    })
            }

            Products.gasBillByIdentifierProduct.name -> {
                handleServiceBillsOutput(input = ServiceBills.Input(
                    Identifier = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Identifier }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.GasBillsBillIdentifier,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    })
            }

            Products.gasBillByParticipateCodeProduct.name -> {
                handleServiceBillsOutput(input = ServiceBills.Input(
                    Identifier = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Identifier }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ), endPoint = EndPoints.GasBillsParticipateCode,
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    })
            }

            Products.mobileProduct.name -> {
                handleMobileBillOutput(input = CellPhoneBills.Input(
                    LineNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.LineNumber }.Value,
                    Operator = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.Operator }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ),
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    })
            }


            Products.landlinePhoneBillProduct.name -> {
                handleLandLineBillOutput(input = LandLinePhoneBills.Input(
                    LineNumber = invoiceInfoOutput.Query.Parameters.first { it.Key == Parameter.LineNumber }.Value,
                    OTPCode = null,
                    PurchaseKey = invoiceInfoOutput.Invoice.PurchaseKey
                ),
                    handleResultCallback = {
                        handleResultCallback?.invoke(it)
                    })
            }


            else -> {}

        }

    }

    fun callFreewayTollBillsDetailedInquiry(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallFreewayTollBillsDetailed(
            input = input as FreewayTollBillsDetailed.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) { prerequisitesResult ->
                if (prerequisitesResult.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (prerequisitesResult as? FreewayTollBillsDetailed.Input)?.let { inputModel ->
                        callFreewayTollBillsDetailedInquiry(
                            input = inputModel
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun callVehicleAuthenticityInquiryV3(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallVehicleAuthenticityV3(
            input = input as VehicleAuthenticityV3.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? VehicleAuthenticityV3.Input)?.let { inputModel ->
                        callVehicleAuthenticityInquiryV3(
                            input = inputModel
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleInquiryTransferTaxMotorCycle(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTransferTaxMotorcycle(
            input = input as TransferTaxMotorcycle.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? TransferTaxMotorcycle.Input)?.let {
                        handleInquiryTransferTaxMotorCycle(
                            input = it
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun callTransferTaxGetSettlementCertificate(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTransferTaxGetSettlementCertificate(
            input = input as TransferTaxGetSettlementCertificate.Input,
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) { prerequisitesResult ->
                if (prerequisitesResult.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (prerequisitesResult as? TransferTaxGetSettlementCertificate.Input)?.let { inputModel ->
                        callTransferTaxGetSettlementCertificate(inputModel) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun callCarTransferTaxV2Inquiry(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTransferTaxCarV2(
            input = input as TransferTaxCarV2.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) { prerequisitesResult ->
                if (prerequisitesResult.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (prerequisitesResult as? TransferTaxCarV2.Input)?.let { inputModel ->
                        callCarTransferTaxV2Inquiry(inputModel) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun callMotorcycleTransferTaxV2Inquiry(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTransferTaxMotorcycleV2(
            input = input as TransferTaxMotorcycleV2.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) { prerequisitesResult ->
                if (prerequisitesResult.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (prerequisitesResult as? TransferTaxMotorcycleV2.Input)?.let { inputModel ->
                        callMotorcycleTransferTaxV2Inquiry(inputModel) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleCarPlateNumberHistory(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallCarPlateNumberHistory(
            input = input as CarPlateNumberHistory.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? CarPlateNumberHistory.Input)?.let {
                        handleCarPlateNumberHistory(it)
                    }
                }
            }
        }
    }

    fun handleInquiryVehicleAuthenticity(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallVehicleAuthenticity(
            input = input as VehicleAuthenticity.Input,
        ) { output ->
            output?.checkPrerequisites(
                ayanActivity = PishkhanSDK.whyGoogleActivity,
                input = input,
            ) { input ->
                if (input.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (input as? VehicleAuthenticity.Input)?.let {
                        output.Prerequisites?.OTP?.ReferenceNumber?.let { referenceNumber ->
                            VehicleAuthenticity.Input(
                                Identifier = it.Identifier,
                                IdentifierType = it.IdentifierType,
                                NationalCodeBuyer = it.NationalCodeBuyer,
                                NationalCodeOwner = it.NationalCodeOwner,
                                OTPCode = it.OTPCode,
                                OTPReferenceNumber = referenceNumber,
                                PurchaseKey = it.PurchaseKey
                            )
                        }?.let {
                            handleInquiryVehicleAuthenticity(
                                it, handleResultCallback
                            )
                        }
                    }
                }
            }
        }
    }

    fun handleInquiryTransferTaxCar(
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTransferTaxCar(
            input = input as TransferTaxCar.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? TransferTaxCar.Input)?.let {
                        handleInquiryTransferTaxCar(
                            input = it
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleJusticeSharesPortfolioOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallJusticeSharesPortfolio(
            input = input as JusticeSharesPortfolio.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? JusticeSharesPortfolio.Input)?.let {
                        handleJusticeSharesPortfolioOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }


        }
    }

    fun handleSubventionHistoryOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallSubventionHistory(
            input = input as SubventionHistory.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? SubventionHistory.Input)?.let {
                        handleSubventionHistoryOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleTrafficFinesCarOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        endPoint: String,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTrafficFinesCar(
            input = input as TrafficFinesCar.Input, endPoint = endPoint
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? TrafficFinesCar.Input)?.let {
                        handleTrafficFinesCarOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                            endPoint = endPoint
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }

        }
    }

    fun handleTrafficFinesCarSummaryOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        endPoint: String,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallTrafficFinesCarSummary(
            input = input as TrafficFinesCarSummary.Input, endPoint = endPoint
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? TrafficFinesCarSummary.Input)?.let {
                        handleTrafficFinesCarSummaryOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                            endPoint = endPoint
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleIbanByCardNumberOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCall<V3BankIbanInfo.Output>(
            input = input as V3BankIbanInfo.Input, endPoint = EndPoints.v3BankIbanInfo
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? V3BankIbanInfo.Input)?.let {
                        handleIbanByCardNumberOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleIbanByAccountNumberOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCall<V3BankIbanInfo.Output>(
            input = input as V2BankIbanInfo.Input, endPoint = EndPoints.v2BankIbanInfo
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? V2BankIbanInfo.Input)?.let {
                        handleIbanByAccountNumberOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleAccountNumberByIbanOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCall<V3BankIbanInfo.Output>(
            input = input as V1BankIbanInfo.Input, endPoint = EndPoints.v1BankIbanInfo
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? V1BankIbanInfo.Input)?.let {
                        handleAccountNumberByIbanOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handlePostPackageTrackingOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallPostPackagesStatus(
            input = input as PostPackagesStatus.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? PostPackagesStatus.Input)?.let {
                        handlePostPackageTrackingOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleBankChequeStatusSayadOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallBankChequeStatusSayad(
            input = input as BankChequeStatusSayad.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? BankChequeStatusSayad.Input)?.let {
                        handleBankChequeStatusSayadOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleFreewayTollBillsOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallFreewayTollBills(
            input = input as FreewayTollBills.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? FreewayTollBills.Input)?.let {
                        handleFreewayTollBillsOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleTrafficPlanTollCarOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallMunicipalityCarTollBills(
            input = input as MunicipalityCarTollBills.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? MunicipalityCarTollBills.Input)?.let {
                        handleTrafficPlanTollCarOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleAnnualTollCarOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        val municipalityInput = input as MunicipalityCarAnnualTollBills.Input

        PishkhanSDK.serviceApi.simpleCallMunicipalityCarAnnualTollBills(input = municipalityInput) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) { prerequisiteError ->
                if (prerequisiteError == null) {
                    handleResultCallback?.invoke(output)
                } else {
                    PishkhanSDK.serviceApi.simpleCallMunicipalityCarAnnualTollBills(input = municipalityInput) { secondOutput ->
                        secondOutput?.let { handleResultCallback?.invoke(it) }
                    }
                }
            }
        }
    }

    fun handlePlateNumbersOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallVehiclePlateNumbers(
            input = input as VehiclePlateNumbers.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? VehiclePlateNumbers.Input)?.let {
                        handlePlateNumbersOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleNegativePointOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallDrivingLicenseNegativePoint(
            input = input as DrivingLicenseNegativePoint.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? DrivingLicenseNegativePoint.Input)?.let {
                        handleNegativePointOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleDrivingLicenceStatusOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallDrivingLicenseStatus(
            input = input as DrivingLicenseStatus.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? DrivingLicenseStatus.Input)?.let {
                        handleDrivingLicenceStatusOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleIdentificationDocumentsStatusCarOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallIdentificationDocumentsStatusCar(
            input = input as IdentificationDocumentsStatusCar.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? IdentificationDocumentsStatusCar.Input)?.let {
                        handleIdentificationDocumentsStatusCarOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleThirdPartyInsuranceOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallVehicleThirdPartyInsurance(
            input = input as VehicleThirdPartyInsurance.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? VehicleThirdPartyInsurance.Input)?.let {
                        handleThirdPartyInsuranceOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleThirdPartyInsuranceStatusOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallVehicleThirdPartyInsuranceStatus(
            input = input as VehicleThirdPartyInsuranceStatus.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? VehicleThirdPartyInsuranceStatus.Input)?.let {
                        handleThirdPartyInsuranceStatusOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handlePassportStatusOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallPassportStatus(
            input = input as PassportStatus.Input
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? PassportStatus.Input)?.let {
                        handlePassportStatusOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleServiceBillsOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        endPoint: String,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallServiceBills(
            input = input as ServiceBills.Input,
            endPoint = endPoint
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? ServiceBills.Input)?.let {
                        handleServiceBillsOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                            endPoint = endPoint
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleMobileBillOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallCellPhoneBills(
            input = input as CellPhoneBills.Input,
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? CellPhoneBills.Input)?.let {
                        handleMobileBillOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

    fun handleLandLineBillOutput(
        apiCalledFromTransactionsFragment: Boolean = false,
        input: BaseInputModel,
        handleResultCallback: ((output: BaseResultModel<*>) -> Unit)? = null
    ) {
        PishkhanSDK.serviceApi.simpleCallLandLinePhoneBills(
            input = input as LandLinePhoneBills.Input,
        ) { output ->
            output?.checkPrerequisites(PishkhanSDK.whyGoogleActivity, input) {
                if (it.isNull()) {
                    handleResultCallback?.invoke(output)
                } else {
                    (it as? LandLinePhoneBills.Input)?.let {
                        handleLandLineBillOutput(
                            apiCalledFromTransactionsFragment = apiCalledFromTransactionsFragment,
                            input = it,
                        ) {
                            handleResultCallback?.invoke(output)
                        }
                    }
                }
            }
        }
    }

}
