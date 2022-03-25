package com.example.lottry.ui.activity.registration

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lottry.R
import com.example.lottry.application.WikiApplication
import com.example.lottry.databinding.ActivityRegistrationBinding
import com.example.lottry.ui.activity.login.LoginActivity
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.utils.Constant
import com.softs.meetupfellow.components.activity.CustomAppActivityCompatViewImpl

class RegistrationActivity : CustomAppActivityCompatViewImpl() {
    lateinit var btnCreateAcc: Button

    lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: RegistrationViewModel
    var phoneNumber: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)

        binding = DataBindingUtil.setContentView(
            this@RegistrationActivity,
            R.layout.activity_registration
        )
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        component.inject(this@RegistrationActivity)
        setInstance(this@RegistrationActivity)

        init()

        binding.signInBtnCreateAcc.setOnClickListener(View.OnClickListener {
//            val intent= Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finish()
            binding.progessBar.visibility=View.VISIBLE
            if (validation()) {

                viewModel.registerUser(this@RegistrationActivity,binding,binding.signInEdtMobileNo.text.toString(),binding.signInEdtUserName.text.toString(),binding.signInEdtRefferalCode.text.toString())
                    .observe(this, Observer {

                        if(it!=null){

                            if(it.getSuccess()!!){

                                showToast(resources.getString(R.string.you_are_registered_successfully))

                                Handler().postDelayed(Runnable {

                                    finish()

                                },5000)

//                                switchActivityOnly(Constant.Intent.Main, true)


                            }
                        }

                    })

            }
        })
    }

    fun init() {

        if (intent.hasExtra(Constant.BundelConstant.PHONE_NO)) {

            phoneNumber = intent.getStringExtra(Constant.BundelConstant.PHONE_NO).toString()
        }

        binding.signInEdtMobileNo.setText("" + phoneNumber)
    }

    private fun validation(): Boolean {

        if (binding.signInEdtUserName.text.isNullOrEmpty() || binding.signInEdtUserName.text.isNullOrBlank()) {

            binding.progessBar.visibility = View.GONE
            showToast(resources.getString(R.string.please_enter_user_name))
            return false

        } else {

            return true
        }

    }
}