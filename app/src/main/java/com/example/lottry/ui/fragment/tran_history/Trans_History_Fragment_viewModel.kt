package com.example.lottry.ui.fragment.tran_history

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.application.WikiApplication
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.request.Request_transHistory
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.data.remote.retrofit.response.Transactionhistory
import com.example.lottry.databinding.TranHistoryFragmentBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.softs.meetupfellow.components.activity.CustomAppActivity
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

class Trans_History_Fragment_viewModel : ViewModel() {

    private val TAG = "Trans_History_Fragment_viewModel"
    private val commonResponse: MutableLiveData<Response_Common> =
        MutableLiveData<Response_Common>()

    lateinit var activity: CustomAppActivity
    var binding: TranHistoryFragmentBinding? = null
    lateinit var sharedPreferencesUtil : SharedPreferencesUtil
    @Inject
    lateinit var  apis: Apis

    init {

        WikiApplication.component!!.inject(this@Trans_History_Fragment_viewModel)
    }

    fun getTransList(
        activity: CustomAppActivity,
        binding: TranHistoryFragmentBinding
    ): LiveData<Response_Common> {

        this.activity = activity
        this.binding = binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)
        binding.progessBar.visibility = View.VISIBLE
        var requestTranshistory =Request_transHistory()

        requestTranshistory.limit=10
        requestTranshistory.page=1

        getTransListFromApi(requestTranshistory)

        return commonResponse
    }

    fun getTransListFromApi(param: Request_transHistory) {
        val call: Call<Response_Common> =
            sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
                apis.getTransHistory(
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
                    binding!!.progessBar.visibility= View.GONE
                    commonResponse.value=response.body()!!

                }else {

                    binding!!.progessBar.visibility= View.GONE
                    Log.e("Home_Fragment_Lottery_Response",response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
                binding!!.progessBar.visibility= View.GONE
                activity.showToast(t.toString())
            }
        })
    }


}