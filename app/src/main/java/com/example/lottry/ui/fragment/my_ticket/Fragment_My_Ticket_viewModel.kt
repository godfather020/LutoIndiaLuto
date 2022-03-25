package com.example.lottry.ui.fragment.my_ticket

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.Request_Lottery
import com.example.lottry.data.remote.retrofit.request.Request_buyTicket
import com.example.lottry.data.remote.retrofit.request.Request_myTicket
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.databinding.FragmentBuyTicketBinding
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.databinding.FragmentMyTicketBinding
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.softs.meetupfellow.components.activity.CustomAppActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Fragment_My_Ticket_viewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis



    lateinit var activity: CustomAppActivity
    lateinit var binding: FragmentMyTicketBinding
    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    //    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    init {

        WikiApplication.component!!.inject(this@Fragment_My_Ticket_viewModel)
    }


    fun get_TicketList(activity: CustomAppActivity, binding: FragmentMyTicketBinding): MutableLiveData<Response_Common> {

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        val requestMyTicket= Request_myTicket()

        requestMyTicket.limit=10
        requestMyTicket.page=1
        requestMyTicket.purchaseDate=""

        getTicketListFromApi(requestMyTicket)

        return commonResponse
    }


    fun getTicketListFromApi(param: Request_myTicket){


        val call: Call<Response_Common> =
            sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
                apis.myTicket(
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





}