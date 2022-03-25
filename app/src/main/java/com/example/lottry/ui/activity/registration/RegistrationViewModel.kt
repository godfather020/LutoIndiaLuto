package com.example.lottry.ui.activity.registration

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.R
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.Request_Login
import com.example.lottry.data.remote.retrofit.request.Request_Registration
import com.example.lottry.data.remote.retrofit.request.Request_Verify
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.databinding.ActivityRegistrationBinding
import com.example.lottry.utils.Constant
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.softs.meetupfellow.components.activity.CustomAppActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RegistrationViewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis



    lateinit var activity:CustomAppActivity
    lateinit var binding:ActivityRegistrationBinding
//    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()

    init {

        WikiApplication.component!!.inject(this@RegistrationViewModel)
    }



    fun registerUser(activity:CustomAppActivity,binding:ActivityRegistrationBinding,phoneNumber:String,name:String,refferalCode:String):MutableLiveData<Response_Common>{

        this.activity=activity
        this.binding=binding

        val requestRegistration=Request_Registration()

        requestRegistration.phoneNumber=phoneNumber
        requestRegistration.userName=name
        requestRegistration.refferalCode=refferalCode

        getResposneOfRegistrationFromApi(requestRegistration)

        return commonResponse
    }

   fun getResposneOfRegistrationFromApi(param:Request_Registration){


   val call:Call<Response_Common> = apis.getRegistraion(param)


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


