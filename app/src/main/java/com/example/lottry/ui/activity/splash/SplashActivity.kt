package com.example.lottry.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lottry.R
import com.example.lottry.application.WikiApplication
import com.example.lottry.ui.activity.get_start.Get_Started_Activity
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.softs.meetupfellow.components.activity.CustomAppActivityCompatViewImpl

class SplashActivity : CustomAppActivityCompatViewImpl(){

   lateinit var constrainLay:ConstraintLayout
   var firstTime:Boolean=true
   var login:Boolean=false
    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        component.inject(this@SplashActivity)
        setInstance(this@SplashActivity)
        sharedPreferencesUtil= SharedPreferencesUtil(this@SplashActivity)
        login= sharedPreferencesUtil.getBoolean(Constant.sharedPrefrencesConstant.LOGIN)!!
        firstTime= sharedPreferencesUtil.isFirstTimeLaunch()

     Handler(Looper.myLooper()!!).postDelayed({

         if(firstTime){
         switchActivityOnly(Constant.Intent.Get_Started,true)
         }else {
             if(!login)
             switchActivityOnly(Constant.Intent.Login, true)
             else
                 switchActivityOnly(Constant.Intent.Main, true)
         }

     },3000)
    }

}