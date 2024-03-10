package ir.ayantech.pishkhansdk.model.api

import com.alirezabdn.generator.AyanAPI
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.model.constants.EndPoints

@AyanAPI(endPoint = EndPoints.UserTransactions)
class UserTransactions {

    data class Output(
        val Filters: List<Filter>?,
        val TotalAmount: Long,
        val TotalNumber: Long,
        val Transactions: List<Transaction>?
    )

    data class Filter(
        val Name: String,
        val ShowName: String,
        val Types: List<Type>
    )

    data class Transaction(
        val Amount: Long,
        val Category: Type,
        val DateTime: String?,
        val Description: String?,
        val Labels: String?,
        val PaymentChannel: Type?,
        val PaymentGateway: Type,
        val Reference: String?,
        val Title: String?,
        val Type: Type,
        val UniqueID: String,
    ) {
        val transactionType: TransactionType
            get() = TransactionType(category = this.Category, type = this.Type)
    }

    class TransactionType(
        var category: Type,
        val type: Type,
    )
}