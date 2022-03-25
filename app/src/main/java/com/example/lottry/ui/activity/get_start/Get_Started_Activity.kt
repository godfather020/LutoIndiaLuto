package com.example.lottry.ui.activity.get_start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.lottry.R
import com.example.lottry.application.WikiApplication
import com.example.lottry.ui.activity.login.LoginActivity
import com.example.lottry.ui.activity.registration.RegistrationActivity
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.softs.meetupfellow.components.activity.CustomAppActivityCompatViewImpl

class Get_Started_Activity : CustomAppActivityCompatViewImpl(){

    lateinit var btnStarted:Button
    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        component.inject(this@Get_Started_Activity)
        sharedPreferencesUtil= SharedPreferencesUtil(this@Get_Started_Activity)

        btnStarted=findViewById(R.id.get_starte_btn_started)

        btnStarted.setOnClickListener(View.OnClickListener {
//            val intent= Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//            finish()

            sharedPreferencesUtil.saveBoolean(Constant.sharedPrefrencesConstant.FIRST_TIME,false)
            switchActivityOnly(Constant.Intent.Login, true)

        })
    }

}