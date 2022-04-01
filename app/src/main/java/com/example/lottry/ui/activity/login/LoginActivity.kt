package com.example.lottry.ui.activity.login

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.UserDetail
import com.example.lottry.databinding.ActivityLoginBinding
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.softs.meetupfellow.components.activity.CustomAppActivityCompatViewImpl
import java.util.regex.Pattern

class LoginActivity : CustomAppActivityCompatViewImpl() {

    lateinit var btnSubmit:Button
    lateinit var viewModel: LoginViewModel
    lateinit var binding:ActivityLoginBinding
    lateinit var phoneNumber:String
    val permission = ArrayList<String>()

    lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      init();
        if (check_Permission()) {

            init()
        } else {
            requestPermission()
        }

    }
    fun init(){

        component.inject(this@LoginActivity)
        setInstance(this@LoginActivity)

        sharedPreferencesUtil= SharedPreferencesUtil(this)
        binding=DataBindingUtil.setContentView(this@LoginActivity,R.layout.activity_login)
        viewModel=ViewModelProvider(this@LoginActivity)[LoginViewModel::class.java]

        receiveSms()

        binding.loginEdtMobileNo.afterTextChanged {


            if(it.length>=10){
                binding.progessBar.visibility= View.VISIBLE
                phoneNumber=it
                getOtp()

            }

        }
        /*binding.otpView.otpListener= object :OTPListener{
            override fun onInteractionListener() {
//                TODO("Not yet implemented")
            }

            override fun onOTPComplete(otp: String?) {
                binding.otpView.showSuccess()
            }
        }*/

        btnSubmit=findViewById(R.id.login_btn_submit)

        btnSubmit.setOnClickListener(View.OnClickListener {

            binding.progessBar.visibility= View.VISIBLE
            if(validation()){


                viewModel.verify_otp(this,binding,binding.loginEdtMobileNo.text.toString(),binding.otpView.otp.toString()).observe(this@LoginActivity, Observer {


                    if(it!=null){

                        if(it.getSuccess()!!){

                            var userDetail=UserDetail()
                            userDetail.id= it.getData()!!.result!!.id.toInt()
                            userDetail.userName= it.getData()!!.result!!.userName
                            userDetail.phoneNumber= it.getData()!!.result!!.phoneNumber
                            userDetail.refferalcode= it.getData()!!.result!!.refferalcode
                            if(it.getData()!!.result!!.profilePic!=null) {
                                userDetail.profilePic = it.getData()!!.result!!.profilePic
                            }

                            sharedPreferencesUtil.saveUserData(userDetail)
                            sharedPreferencesUtil.saveString(Constant.sharedPrefrencesConstant.X_TOKEN,it.getData()!!.token)
                            sharedPreferencesUtil.saveBoolean(Constant.sharedPrefrencesConstant.LOGIN,true)



                            binding.progessBar.visibility= View.GONE
                            switchActivityOnly(Constant.Intent.Main, true)
                        }
                    }else {
                        binding.progessBar.visibility = View.GONE
                        showToast(resources.getString(R.string.you_have_no_data))
                    }
                })
            }




        })
    }

    private fun receiveSms() {
        val br = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {

                for (sms : SmsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)){
                    //Toast.makeText(applicationContext,sms.displayMessageBody, Toast.LENGTH_LONG).show()
                    val smsBody = sms.messageBody
                    Log.d("msgBody", smsBody)
                    getOtpFromMessage(smsBody)
                    //val getOtp = smsBody.split("Your OTP: ").toTypedArray()[1]
                    //Log.d("otp", getOtp)
                    //setOtp(getOtp)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    private fun getOtpFromMessage(message: String) {
        // This will match any 6 digit number in the message
        val pattern = Pattern.compile("(|^)\\d{4}")
        val matcher = pattern.matcher(message)
        if (matcher.find()) {
            Log.d("Otp", matcher.group(0))
            setOtp(matcher.group(0).toString())
        }
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun onRestart() {
        super.onRestart()
        init()
    }

    private fun setOtp(otp: String) {

        hideKeyBoardOnTouchScreen(this.currentFocus!!.rootView)
        binding.otpView.setOTP(otp)

        }
    private fun getOtp(){

        viewModel.get_otp(this,binding,phoneNumber).observe(this@LoginActivity, Observer {

            if(it!=null){

                Toast.makeText(applicationContext, "Otp sent to your number.", Toast.LENGTH_LONG).show()
                //Log.d("responseOtp", it.getData()!!.otp)
                //setOtp(it.getData()!!.otp)

            }else
                showToast(resources.getString(R.string.you_have_no_data))

        })
    }

    private fun validation():Boolean{

        if(binding.loginEdtMobileNo.text.isNullOrEmpty()||binding.loginEdtMobileNo.text.isNullOrBlank()){

            binding.progessBar.visibility= View.GONE
            showToast(resources.getString(R.string.please_enter_phone_number))
            return false
        }else if(binding.loginEdtMobileNo.text!!.length<10){

            binding.progessBar.visibility= View.GONE
            showToast(resources.getString(R.string.please_enter_phone_number))
            return false
        }else if(binding.otpView.otp.isNullOrEmpty()||binding.otpView.otp.isNullOrEmpty()){

            binding.progessBar.visibility= View.GONE
            showToast(resources.getString(R.string.please_enter_otp))
            return false
        }
        else {

            return true
        }



        }

    fun check_Permission(): Boolean {
        val camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        val gallery =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val gallery1 =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val contact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        val phone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val recieveSms = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
        if (camera != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA)
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.INTERNET)
        }
        if (gallery != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (gallery1 != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (contact != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_CONTACTS)
        }
        if (phone != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (recieveSms != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.RECEIVE_SMS)
        }
        return if (!permission.isEmpty()) {
            false
        } else true
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            permission.toTypedArray<String>(),
            Companion.REQUEST_ID_MULTIPLE_PERMISSIONS
        )
    }

    companion object {
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 3
    }



}



