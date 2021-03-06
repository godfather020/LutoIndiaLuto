package com.example.lottry.ui.activity.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.Request_setProfileImg
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.databinding.ActivityMainBinding
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.softs.meetupfellow.components.activity.CustomAppActivity
import javax.inject.Inject


class MainViewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis



    lateinit var activity:CustomAppActivity
    lateinit var binding: ActivityMainBinding
//    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()
   lateinit var sharedPreferencesUtil:SharedPreferencesUtil


    init {

        WikiApplication.component!!.inject(this@MainViewModel)

    }



    fun set_profileImage(activity:CustomAppActivity,binding: ActivityMainBinding,imgUrl:String):MutableLiveData<Response_Common>{

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        val requestSetprofileimg=Request_setProfileImg()

        requestSetprofileimg.image = imgUrl

        setImgFromApi(requestSetprofileimg)
        Log.d("reqsetimg", requestSetprofileimg.toString())
        return commonResponse
    }



   fun setImgFromApi(param:Request_setProfileImg){



   val call: retrofit2.Call<Response_Common> = sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
       apis.setProfileImg(
           it,param.image )}!!

       Log.d("paramimg", param.image)

       call.enqueue(object : retrofit2.Callback<Response_Common> {
           
           override fun onResponse(
               call: retrofit2.Call<Response_Common>,
               response: retrofit2.Response<Response_Common>
           ) {
//               TODO("Not yet implemented")
               Log.d("response", response.toString())
               if(response.isSuccessful) {
//                   binding.progessBar.visibility= View.GONE
                   commonResponse.value=response.body()!!

               }else {

//                   binding.progessBar.visibility= View.GONE
                   val element: JsonElement = Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                   val jsonObject = element.asJsonObject

                   if(jsonObject.get("code").toString().equals("500")){
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){


                           activity.showToast(jsonObject.get("message").asString)


//                       }
                   }

               }
           }

           override fun onFailure(call: retrofit2.Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
//               binding.progessBar.visibility= View.GONE
               activity.showToast(t.toString())
           }
       })
}



}


