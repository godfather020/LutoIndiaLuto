package com.example.lottry.ui.fragment.buy_ticket

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.Request_Lottery
import com.example.lottry.data.remote.retrofit.request.Request_buyTicket
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.databinding.FragmentBuyTicketBinding
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.softs.meetupfellow.components.activity.CustomAppActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Fragment_Buy_Ticket_viewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis



    lateinit var activity: CustomAppActivity
    lateinit var binding: FragmentBuyTicketBinding
    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    //    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    init {



        WikiApplication.component!!.inject(this@Fragment_Buy_Ticket_viewModel)
    }


    fun get_TicketList(activity: CustomAppActivity, binding: FragmentBuyTicketBinding): MutableLiveData<Response_Common> {

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        val requestLottery= Request_Lottery()

        requestLottery.limit=10
        requestLottery.page=1

        getTicketListFromApi(requestLottery)

        return commonResponse
    }
    fun buyTicket(activity: CustomAppActivity, reqestBuyTicket:Request_buyTicket): MutableLiveData<Response_Common> {

        this.activity=activity
        sharedPreferencesUtil= SharedPreferencesUtil(activity)


        getBuyTickeResponseFromApi(reqestBuyTicket)

        return commonResponse1
    }


    fun getTicketListFromApi(param: Request_Lottery){


        val call: Call<Response_Common> =
            sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
                apis.getTickeList(
                    it)
            }!!


        call.enqueue(object : Callback<Response_Common> {

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

fun getBuyTickeResponseFromApi(param: Request_buyTicket){


        val call: Call<Response_Common> =
            sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
                apis.buyTicket(
                    it,param)
            }!!


        call.enqueue(object : Callback<Response_Common> {

            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {
//               TODO("Not yet implemented")
                if(response.isSuccessful) {

                    response.body()!!.message?.let {  }
                    commonResponse1.value=response.body()!!


                }else {
                    commonResponse1.value=null

                    Log.e("Home_Fragment_Lottery_Response",response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
                commonResponse1.value=null
                activity.showToast(t.toString())
            }
        })
    }



}