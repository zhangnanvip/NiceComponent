package com.znvip.nicecomponent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.znvip.nicecomponent.entry.NiceBottomSheetActivity
import com.znvip.nicecomponent.entry.NicePopActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_nice_pop.setOnClickListener(this)
        btn_nice_bottom_sheet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_nice_pop -> NicePopActivity.start(this)
            R.id.btn_nice_bottom_sheet -> NiceBottomSheetActivity.start(this)
        }
    }
}
