package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.PishkhansdkBottomSheetSelectBankCardNumberBinding
import ir.ayantech.pishkhansdk.model.api.CnpgGetCardList
import ir.ayantech.pishkhansdk.model.api.CnpgRemoveCard
import ir.ayantech.pishkhansdk.model.constants.EndPoints
import ir.ayantech.pishkhansdk.ui.adapter.PishkhansdkUserBankCardsAdapter
import ir.ayantech.pishkhansdk.ui.components.getText
import ir.ayantech.pishkhansdk.ui.components.init
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.verticalSetup

open class PishkhansdkSelectBankCardNumberBottomSheet(
    private val context: Context,
    var bankCards: List<CnpgGetCardList.BankCard>,
    private val onRemoveCardConfirmed: (bankCard: CnpgGetCardList.BankCard) -> Unit,
    private val onBankCardEntered: (bankCard: CnpgGetCardList.BankCard) -> Unit
): BaseBottomSheet<PishkhansdkBottomSheetSelectBankCardNumberBinding>(
    context
) {
    override val binder: (LayoutInflater) -> PishkhansdkBottomSheetSelectBankCardNumberBinding
        get() = PishkhansdkBottomSheetSelectBankCardNumberBinding::inflate

    override val title: String?
        get() = getString(R.string.choose_bank_card_number)

    override fun onCreate() {
        super.onCreate()

        accessViews {
            bankCardNumberInputComponent.init(
                context = context,
                hint = getString(R.string.bank_card_number),
                inputType = InputType.TYPE_CLASS_NUMBER,
                maxLength = 16
            )

            confirmBankCardNumberButtonComponent.init(
                btnText = getString(R.string.confirm)
            ) {
                onBankCardEntered(
                    CnpgGetCardList.BankCard(
                        MaskedPan = bankCardNumberInputComponent.getText(),
                        BankID = 0L,
                        CardID = ""
                    )
                )
                dismiss()
            }

            userBankCardsRv.apply {
                verticalSetup()
                adapter = PishkhansdkUserBankCardsAdapter(
                    items = bankCards,
                    onDeleteIvClicked = { selectedBankCard ->
                        PishkhansdkRemoveBankCardConfirmationBottomSheet(
                            context = context,
                            cardNumber = selectedBankCard.MaskedPan,
                            onCardRemoveConfirmed = {
                                onRemoveCardConfirmed(selectedBankCard)
                            }
                        ).show()
                    },
                    onItemClickListener = { item, viewId, position ->
                        item?.let { selectedBankCard ->
                            onBankCardEntered(selectedBankCard)
                            dismiss()
                        }
                    }
                )
            }
        }
    }

    fun updateBankCards(bankCard: CnpgGetCardList.BankCard) {
        val newList = bankCards.toMutableList()
        newList.remove(bankCard)
        (binding.userBankCardsRv.adapter as? PishkhansdkUserBankCardsAdapter)?.updateItems(newList)
        bankCards = newList
    }

}