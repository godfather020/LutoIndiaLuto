package com.example.lottry.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.R
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.*
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.databinding.ActivityLoginBinding
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.ui.activity.main.MainActivity
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





class Fragment_Home_viewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis



    lateinit var activity:CustomAppActivity
    lateinit var binding: FragmentHomeBinding
    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
//    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    init {

        WikiApplication.component!!.inject(this@Fragment_Home_viewModel)
    }



    fun get_LotteryDetail(activity:CustomAppActivity,binding: FragmentHomeBinding):MutableLiveData<Response_Common>{

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        val requestLottery=Request_Lottery()

       requestLottery.limit=10
        requestLottery.page=1

        getLotteryResponseFromApi(requestLottery)

        return commonResponse
    }

    fun get_TopWinners(activity:CustomAppActivity,binding: FragmentHomeBinding):MutableLiveData<Response_Common>{

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        val requestTopwinners=Request_topWinners()

       requestTopwinners.limit=10
        requestTopwinners.page=1
        requestTopwinners.date=""

        getTopWinnersFromApi(requestTopwinners)

        return commonResponse1
    }

    fun get_walletBalance(activity:CustomAppActivity,binding: FragmentHomeBinding){

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)


        getWalletBalanceFromApi()
    }


   fun getLotteryResponseFromApi(param:Request_Lottery){


   val call:Call<Response_Common> =
       sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
           apis.getLotteryDetail(
               it,param)
       }!!


       call.enqueue(object : Callback<Response_Common>{
           
           @SuppressLint("LongLogTag")
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
                   Log.e("Home_Fragment_Lottery_Response",response.errorBody()!!.string())
               }
           }

           override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
               binding.progessBar.visibility= View.GONE
               activity.showToast(t.toString())
           }
       })
}

 fun getTopWinnersFromApi(param:Request_topWinners){


   val call:Call<Response_Common> =
       sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
           apis.getTopWinners(
               it,param)
       }!!


       call.enqueue(object : Callback<Response_Common>{

           @SuppressLint("LongLogTag")
           override fun onResponse(
               call: Call<Response_Common>,
               response: Response<Response_Common>
           ) {
//               TODO("Not yet implemented")
               if(response.isSuccessful) {
                   binding.progessBar.visibility= View.GONE
                   commonResponse1.value=response.body()!!

               }else {

                   binding.progessBar.visibility= View.GONE
                   Log.e("Home_Fragment_Lottery_Response",response.errorBody()!!.string())
               }
           }

           override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
               binding.progessBar.visibility= View.GONE
               activity.showToast(t.toString())
           }
       })
}

 fun getWalletBalanceFromApi(){


   val call:Call<Response_Common> =
       sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
           apis.getWalletBalance(
               it)
       }!!


       call.enqueue(object : Callback<Response_Common>{

           @SuppressLint("LongLogTag")
           override fun onResponse(
               call: Call<Response_Common>,
               response: Response<Response_Common>
           ) {
//               TODO("Not yet implemented")
               if(response.isSuccessful) {
                   binding.progessBar.visibility= View.GONE
                   sharedPreferencesUtil.saveInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE,response.body()!!.getData()!!.result!!.wallet)

               }else {

                   binding.progessBar.visibility= View.GONE
                   Log.e("Home_Fragment_Lottery_Response",response.errorBody()!!.string())
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


