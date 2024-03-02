package ir.ayantech.pishkhansdk.ui.fragments

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhansdk.R
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import ir.ayantech.whygoogle.fragment.ViewBindingInflater
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import ir.ayantech.whygoogle.fragment.WhyGoogleFragmentTransactionAnimation
import ir.ayantech.whygoogle.helper.trying

abstract class AyanFragment<T : ViewBinding> : WhyGoogleFragment<T>() {

    companion object {
        var selectedTabPosition = -1
        var isFooterActive = true
    }

    abstract var pageTitle: String

    open val showToolbar = true
    open val showToolbarInfoIcon = false
    open val toolbarDescription: String? = null
    open val toolbarActionIcon: Int? = null
    open fun getToolbarAction() {}

    var queryString: List<String>? = null

    var insideTab = false

    override fun onCreate() {
        super.onCreate()

        if (showToolbar)
            manageHeader()
    }

    override fun onFragmentVisible() {
        super.onFragmentVisible()

        hideKeyboard()
    }

    fun manageHeader() {
/*        (headerBinding as? ComponentHeaderBinding)?.apply {
            this.init(
                toolbarTitle = pageTitle,
                toolbarDescription = toolbarDescription,
                showToolbarInfoIv = showToolbarInfoIcon,
                toolbarActionIcon = toolbarActionIcon,
                backIvClickedCallback = {
                    onBackPressed()
                },
                infoIvClickedCallback = {
                    headerContentsRl.delayedTransition()
                    toolbarDescriptionTv.changeVisibility(!toolbarDescriptionTv.isVisible())
                },
                actionIconClickedCallback = {
                    getToolbarAction()
                }
            )
        }*/
    }


    fun showKeyboard(view: View) {
        trying {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard() {
        trying {
            val imm =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = requireActivity().currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun getFragmentTransactionAnimation(context: Context): WhyGoogleFragmentTransactionAnimation? {
        return if ((context as WhyGoogleActivity<*>).getFragmentCount() > 0)
            WhyGoogleFragmentTransactionAnimation(
                R.anim.h_fragment_enter,
                R.anim.h_fragment_exit,
                R.anim.h_fragment_pop_enter,
                R.anim.h_fragment_pop_exit
            )
        else
            null
    }

    fun delayedTransition(transition: Transition? = null) {
        if (transition == null) {
            TransitionManager.beginDelayedTransition(
                binding.root as ViewGroup,
                TransitionSet().apply {
                    ordering = TransitionSet.ORDERING_SEQUENTIAL
                    addTransition(ChangeBounds())
                    addTransition(Fade(Fade.IN))
                }
            )
        } else
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)
    }


/*    override val headerInflater: ViewBindingInflater?
        get() = if (showToolbar && !insideTab) ComponentHeaderBinding::inflate else null*/
}
