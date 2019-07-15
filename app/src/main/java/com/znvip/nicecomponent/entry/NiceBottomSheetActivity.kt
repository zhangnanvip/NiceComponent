package com.znvip.nicecomponent.entry

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.znvip.nicebottomsheet.NiceBottomSheet
import com.znvip.nicecomponent.R
import kotlinx.android.synthetic.main.activity_nice_bottom_sheet.*

/**
 * @author zhangnan
 * @date 2019-07-15
 */
class NiceBottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nice_bottom_sheet)
        btn_bottom_sheet_text.setOnClickListener {
            NiceBottomSheet.newInstance()
                    // default title view
                    .configDefaultTitleView {
                        // do something with titleView
                        return@configDefaultTitleView "文字（默认头部）"
                    }
                    .setUpdateUIHandler {
                        it.setContentView(TextView(this).apply {
                            setPadding(16)
                            text = "文字"
                        })
                    }
                    .showBySelfGrowthTag(supportFragmentManager)
        }
        btn_bottom_sheet_img.setOnClickListener {
            NiceBottomSheet.newInstance()
                    .setUpdateUIHandler {
                        val ll = LinearLayout(this)
                        ll.orientation = LinearLayout.VERTICAL
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        ll.addView(ImageView(this).apply { setImageResource(R.drawable.ic_nice_foreground) })
                        // custom title view
                        it.setTitleView(TextView(this).apply {
                            setPadding(16)
                            text = "图片（自定义头部）"
                            gravity = Gravity.CENTER
                            setTextColor(Color.BLACK)
                            setBackgroundColor(ContextCompat.getColor(this@NiceBottomSheetActivity, R.color.colorAccent2))
                        }).setContentView(ll)
                    }
                    .showBySelfGrowthTag(supportFragmentManager)
        }
    }

    companion object {

        fun start(context: Context) = context.startActivity(Intent(context, NiceBottomSheetActivity::class.java))

    }
}