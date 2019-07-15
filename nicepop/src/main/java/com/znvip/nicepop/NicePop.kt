package com.znvip.nicepop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.layout_nice_pop.view.*

/**
 * @author zhangnan
 * @date 2019-07-15
 */
class NicePop(context: Context?) : PopupWindow(context, null, R.style.WindowTransparent) {

    var showArrow: Boolean = false

    init {
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isOutsideTouchable = true
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        if (anchor != null) {
            // 是否显示箭头
            if (showArrow) {
                contentView.iv_up_arrow.visibility = View.VISIBLE
                contentView.iv_down_arrow.visibility = View.VISIBLE
            } else {
                contentView.iv_up_arrow.visibility = View.GONE
                contentView.iv_down_arrow.visibility = View.GONE
            }

            // 测量弹窗宽高（只有当弹窗指定宽高时才可以自适应弹窗方向）
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            width = contentView.measuredWidth
            height = contentView.measuredHeight - (contentView.iv_up_arrow.measuredHeight + contentView.iv_down_arrow.measuredHeight) / 2

            // 显示弹窗
            super.showAsDropDown(anchor, xoff, yoff, gravity)

            // 如果显示箭头对箭头位置进行调整
            if (showArrow) {
                contentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        adjustArrowLocation(anchor)
                        contentView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                })
            }
        }
    }

    override fun setContentView(contentView: View) {
        val parent = LayoutInflater.from(contentView.context).inflate(R.layout.layout_nice_pop, null)
        parent.fl_content.addView(contentView)
        super.setContentView(parent)
    }

    /**
     * @param anchorView 锚 View
     *
     * Adjust arrow location.
     */
    private fun adjustArrowLocation(anchorView: View) {
        val contentLoc = IntArray(2)
        contentView.getLocationOnScreen(contentLoc)
        val popLeftLoc = contentLoc[0]
        anchorView.getLocationOnScreen(contentLoc)
        val anchorLeftLoc = contentLoc[0]
        val adjustDistance = anchorLeftLoc - popLeftLoc + anchorView.width / 2 - contentView.iv_up_arrow.width / 2
        if (isAboveAnchor) {
            contentView.iv_up_arrow.visibility = View.GONE
            contentView.iv_down_arrow.visibility = View.VISIBLE
            (contentView.iv_down_arrow.layoutParams as LinearLayout.LayoutParams).leftMargin = adjustDistance
        } else {
            contentView.iv_up_arrow.visibility = View.VISIBLE
            contentView.iv_down_arrow.visibility = View.GONE
            (contentView.iv_up_arrow.layoutParams as LinearLayout.LayoutParams).leftMargin = adjustDistance
        }
    }

    /**
     * @param resId 箭头图片资源 ID
     *
     * Only need up arrow , down arrow will be generated automatically.
     */
    fun setUpArrow(@DrawableRes resId: Int) {
        assert(contentView != null) { "Please setContentView First!" }
        contentView.iv_up_arrow.setImageResource(resId)
        contentView.iv_down_arrow.setImageResource(resId)
    }
}