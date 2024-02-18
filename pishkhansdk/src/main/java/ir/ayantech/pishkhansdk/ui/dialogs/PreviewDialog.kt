package ir.ayantech.pishkhansdk.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.R
import ir.ayantech.pishkhansdk.databinding.DialogPreviewBinding
import ir.ayantech.pishkhansdk.mdoel.InvoiceRegister
import ir.ayantech.pishkhansdk.ui.adapter.SimpleKeyValueAdapter
import ir.ayantech.whygoogle.helper.*

class PreviewDialog(
    context: Context,
    var invoiceOutput: InvoiceRegister.Output,
    var showAmountSection: Boolean,
    val confirmBtnClicked: SimpleCallBack
) : AyanDialog<DialogPreviewBinding>(context) {

    override val binder: (LayoutInflater) -> DialogPreviewBinding
        get() = DialogPreviewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        setupActions()
    }

    private fun setupActions() {
        binding.apply {
            confirmBtn.setOnClickListener {
                dismiss()
                confirmBtnClicked()
            }
        }
    }

    private fun initViews() {
        binding.apply {
            costLl.changeVisibility(showAmountSection)
            payableAmountTv.text = invoiceOutput.Invoice.Payable.formatAmount()
            rulesTitleTv.changeVisibility(invoiceOutput.TermsAndConditions.isNotNull())
            rulesTv.changeVisibility(invoiceOutput.TermsAndConditions.isNotNull())
            rulesTv.text = invoiceOutput.TermsAndConditions

            titleTv.changeVisibility(invoiceOutput.Invoice.Service.Summary.isNotNull())
            extraInfoRv.changeVisibility(invoiceOutput.Invoice.Service.Summary.isNotNull())

            extraInfoRv.verticalSetup()
            extraInfoRv.adapter = invoiceOutput.Invoice.Service.Summary?.let { serviceSummery ->
                SimpleKeyValueAdapter(
                    serviceSummery,
                    startHighlightFromZero = false,
                    keyTvResId = R.style.description_grey700,
                    valueTvResId = R.style.description_primary_dark
                )
            }
        }
    }
}