package com.znvip.nicebottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet_default_title.*
import kotlinx.android.synthetic.main.layout_identify_bottom_sheet.*

class NiceBottomSheet : BottomSheetDialogFragment() {

    private val sheetTag: String = this.javaClass.simpleName + SheetCounter.getInstance().increase()

    var banDrop = false

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private val bottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (banDrop && newState == BottomSheetBehavior.STATE_DRAGGING) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    private var customTitleView: View? = null

    private var configDefaultTitle: ((defaultTitleView: View) -> String)? = null

    private var updateUI: ((NiceBottomSheet) -> Unit)? = null

    private var peekHeight = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_identify_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (customTitleView == null) {
            setTitleLayoutResId(R.layout.layout_bottom_sheet_default_title)
            tv_back.setOnClickListener { dismiss() }
            tv_title.text = configDefaultTitle?.invoke(customTitleView!!) ?: ""
        }
        updateUI?.invoke(this)
    }

    override fun onStart() {
        super.onStart()
        if (peekHeight > 0) {
            view?.post {
                val parent = view?.parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                bottomSheetBehavior = behavior as BottomSheetBehavior<View>?
                bottomSheetBehavior?.setBottomSheetCallback(bottomSheetBehaviorCallback)
                bottomSheetBehavior?.peekHeight = 300
            }
        }
    }

    fun setMinHeight(minHeight: Int): NiceBottomSheet {
        assertUIUpdate()
        fl_content.minimumHeight = minHeight
        fl_content.requestLayout()
        return this
    }

    fun setMarginTop(MarginTop: Int): NiceBottomSheet {
        assertUIUpdate()
        val layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        layoutParams.topMargin = MarginTop
        ll_root.layoutParams = layoutParams
        return this
    }

    /**
     * configDefaultTitleView must invoke before setUpdateUIHandler.
     */
    fun configDefaultTitleView(configDefaultTitle: ((customTitleView: View) -> String)): NiceBottomSheet {
        this.configDefaultTitle = configDefaultTitle
        return this
    }

    fun setUpdateUIHandler(updateUI: ((NiceBottomSheet) -> Unit)): NiceBottomSheet {
        this.updateUI = updateUI
        return this
    }

    fun setTitleLayoutResId(titleLayoutResId: Int): NiceBottomSheet {
        if (titleLayoutResId > 0) {
            setTitleView(layoutInflater.inflate(titleLayoutResId, null))
        }
        return this
    }

    fun setTitleView(view: View): NiceBottomSheet {
        assertUIUpdate()
        fl_title.removeAllViews()
        fl_title.addView(view)
        customTitleView = view
        return this
    }

    fun setContentLayoutResId(contentLayoutResId: Int): NiceBottomSheet {
        if (contentLayoutResId > 0) {
            setContentView(layoutInflater.inflate(contentLayoutResId, fl_content))
        }
        return this
    }

    fun setContentView(view: View): NiceBottomSheet {
        assertUIUpdate()
        fl_content.removeAllViews()
        fl_content.addView(view)
        return this
    }

    fun showBySelfGrowthTag(manager: FragmentManager): NiceBottomSheet {
        super.show(manager, sheetTag)
        return this
    }

    fun showByTag(manager: FragmentManager?, tag: String?): NiceBottomSheet {
        super.show(manager, tag)
        return this
    }

    fun showWithPeekHeight(manager: FragmentManager?, peekHeight: Int): NiceBottomSheet {
        showWithPeekHeightAndTag(manager, peekHeight, sheetTag)
        return this
    }

    fun showWithPeekHeightAndTag(manager: FragmentManager?, peekHeight: Int, tag: String?): NiceBottomSheet {
        this.peekHeight = peekHeight
        super.show(manager, tag)
        return this
    }

    fun peekTag(): String {
        return sheetTag
    }

    private fun assertUIUpdate() {
        assert(view != null) { "Please invoke this function in UpdateUIListener!" }
    }

    companion object {
        fun newInstance() = NiceBottomSheet()
    }

}