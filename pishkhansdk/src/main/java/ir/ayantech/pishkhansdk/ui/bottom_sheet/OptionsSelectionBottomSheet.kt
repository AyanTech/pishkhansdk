package ir.ayantech.pishkhansdk.ui.bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhansdk.databinding.BottomSheetOptionsSelectionBinding
import ir.ayantech.pishkhansdk.model.app_logic.ExtraInfo
import ir.ayantech.pishkhansdk.model.app_logic.Type
import ir.ayantech.pishkhansdk.ui.adapter.OptionsAdapter
import ir.ayantech.whygoogle.helper.addDivider
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.verticalSetup

class OptionsSelectionBottomSheet(
    context: Context,
    override val title: String?,
    private val descriptionKeyValue: ExtraInfo?,
    private val options: List<Type>,
    private val callback: NameShowNameCallBack,
) : BaseBottomSheet<BottomSheetOptionsSelectionBinding>(context) {

    override fun onCreate() {
        super.onCreate()
        binding.apply {
            titleTv.text = title
            descriptionLl.changeVisibility(descriptionKeyValue.isNotNull())
            dividerIv.changeVisibility(descriptionKeyValue.isNotNull())
            descriptionKeyTv.text = descriptionKeyValue?.Key
            descriptionValueTv.text = descriptionKeyValue?.Value

            optionsRv.verticalSetup()
            optionsRv.addDivider()
            optionsRv.adapter = OptionsAdapter(options) { item, viewId, position ->
                dismiss()
                item?.let { callback(it) }
            }
        }
    }

    override fun show() {
        if (options.size == 1) {
            callback(options.first())
            return
        }
        super.show()
    }

    override val binder: (LayoutInflater) -> BottomSheetOptionsSelectionBinding
        get() = BottomSheetOptionsSelectionBinding::inflate
}

typealias NameShowNameCallBack = (Type) -> Unit
