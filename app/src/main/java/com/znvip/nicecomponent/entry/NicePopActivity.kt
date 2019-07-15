package com.znvip.nicecomponent.entry

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.znvip.nicecomponent.R
import com.znvip.nicepop.NicePop
import kotlinx.android.synthetic.main.activity_nice_pop.*
import kotlin.random.Random

class NicePopActivity : AppCompatActivity() {

    private var lastPop: NicePop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nice_pop)
        btn_start_top.setOnClickListener { showPop(btn_start_top) }
        btn_end_top.setOnClickListener { showPop(btn_end_top) }
        btn_start_bottom.setOnClickListener { showPop(btn_start_bottom) }
        btn_end_bottom.setOnClickListener { showPop(btn_end_bottom) }
        btn_center.setOnClickListener { showPop(btn_center) }
    }

    private fun showPop(view: View) {
        lastPop?.dismiss()
        val pop = NicePop(this)
        pop.contentView = TextView(this).apply {
            setPadding(16)
            setBackgroundResource(R.color.colorAccent1)
            setTextColor(Color.BLACK)
            text = "我在这里"
        }
        pop.showArrow = Random.nextBoolean()
        pop.showAsDropDown(view)
        lastPop = pop
    }

    companion object {

        fun start(context: Context) = context.startActivity(Intent(context, NicePopActivity::class.java))

    }
}