package com.daniel.petrovskyproject

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import de.cketti.mailto.EmailIntentBuilder
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        activity_info_button_back.setOnClickListener(this)
        activity_info_text_view_email.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            activity_info_button_back.id -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
            activity_info_text_view_email.id -> {
                EmailIntentBuilder.from(this)
                        .to("mossur15@gmail.com")
                        .subject("Feedback ${application.resources.getString(R.string.app_name)}")
                        .start()
            }
        }
    }
}
