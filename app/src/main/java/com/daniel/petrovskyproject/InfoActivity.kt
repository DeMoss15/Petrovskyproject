package com.daniel.petrovskyproject

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        activity_info_button_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            activity_info_button_back.id -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
