package com.example.lottry.ui.activity.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.R
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.Request_Login
import com.example.lottry.data.remote.retrofit.request.Request_Verify
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.databinding.ActivityLoginBinding
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtilInterface
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.softs.meetupfellow.components.activity.CustomAppActivity
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.google.gson.JsonObject





class LoginViewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis



    lateinit var activity:CustomAppActivity
    lateinit var binding: ActivityLoginBinding
//    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()


    init {

        WikiApplication.component!!.inject(this@LoginViewModel)
    }



    fun get_otp(activity:CustomAppActivity,binding: ActivityLoginBinding,phoneNumber:String):MutableLiveData<Response_Common>{

        this.activity=activity
        this.binding=binding


        val requestLogin=Request_Login()

        requestLogin.phoneNumber=phoneNumber

        getResposneOfLoginFromApi(requestLogin)

        return commonResponse
    }

    fun verify_otp(activity:CustomAppActivity,binding: ActivityLoginBinding,phoneNumber:String,otp:String):MutableLiveData<Response_Common>{

        this.activity=activity
        this.binding=binding


        val requestVerify=Request_Verify()

        requestVerify.phoneNumber=phoneNumber
        requestVerify.otp=otp
        requestVerify.deviceType="Android"
        requestVerify.deviceToken = Constant.ApiConstant.DEVICE_TOKEN
        Log.d("deviceToken", Constant.ApiConstant.DEVICE_TOKEN )
        //Log.d("deviceTokensh", Constant.sharedPrefrencesConstant.DEVICE_TOKEN)

        getResposneOfVerifyOtpFromApi(requestVerify)

        return commonResponse1
    }

   fun getResposneOfLoginFromApi(param:Request_Login){


       val phoneNumber= param.phoneNumber
   val call:Call<Response_Common> = apis.getOtp(param)


       call.enqueue(object : Callback<Response_Common>{
           
           override fun onResponse(
               call: Call<Response_Common>,
               response: Response<Response_Common>
           ) {
//               TODO("Not yet implemented")
               if(response.isSuccessful) {
                   binding.progessBar.visibility= View.GONE
                   commonResponse.value=response.body()!!

               }else {

                   binding.progessBar.visibility= View.GONE
                   val element: JsonElement = Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                   val jsonObject = element.asJsonObject

                   if(jsonObject.get("code").toString().equals("500")){
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){


                           activity.showToast(jsonObject.get("message").asString)
                           val bundle=Bundle().apply { putString(Constant.BundelConstant.PHONE_NO,phoneNumber)}
                           activity.switchActivity(Constant.Intent.Registration,false,bundle)

//                       }
                   }

               }
           }

           override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
               binding.progessBar.visibility= View.GONE
               activity.showToast(t.toString())
           }
       })
}


    fun getResposneOfVerifyOtpFromApi(param:Request_Verify){


   val call:Call<Response_Common> = apis.getVerifyOtp(param)


       call.enqueue(object : Callback<Response_Common>{

           override fun onResponse(
               call: Call<Response_Common>,
               response: Response<Response_Common>
           ) {
//               TODO("Not yet implemented")
               if(response.isSuccessful) {

//                   sharedPreferencesUtil.saveString(Constant.sharedPrefrencesConstant.X_TOKEN,response.body()!!.getData()!!.token)
                   binding.progessBar.visibility= View.GONE
                   commonResponse1.value=response.body()!!

               }
           }

           override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
               binding.progessBar.visibility= View.GONE
               activity.showToast(t.toString())
           }
       })
}
}


