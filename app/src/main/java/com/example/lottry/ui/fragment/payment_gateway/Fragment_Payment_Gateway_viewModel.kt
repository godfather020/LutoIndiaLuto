package com.example.lottry.ui.fragment.payment_gateway

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
import com.example.lottry.databinding.FragmentPaymentGatewaytBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtilInterface

import com.softs.meetupfellow.components.activity.CustomAppActivity
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Fragment_Payment_Gateway_viewModel : ViewModel() {

    @Inject
    lateinit var  apis: Apis
    lateinit var activity:CustomAppActivity
    lateinit var binding: FragmentPaymentGatewaytBinding
    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
//    val apis:Apis=RetrofitInstance().getmRetrofitInstance()!!.create(Apis::class.java)
    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    init {

        WikiApplication.component!!.inject(this@Fragment_Payment_Gateway_viewModel)
    }



    fun get_paytmToken(activity:CustomAppActivity,binding: FragmentPaymentGatewaytBinding,orderId:String,amt:String,callbackUrl:String):MutableLiveData<Response_Common>{
        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)


        val requestPaytmchecksum=Request_paytmChecksum()
        requestPaytmchecksum.orderId=orderId
        requestPaytmchecksum.amount=amt
        requestPaytmchecksum.callbackUrl=callbackUrl
        getPaytmTokenFromApi(requestPaytmchecksum)


        return commonResponse
    }

    fun add_walletBalance(activity:CustomAppActivity,binding: FragmentPaymentGatewaytBinding,amt: String){

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        var requestAddwalletbalance=Request_addWalletBalance()
        requestAddwalletbalance.amount=amt



        addWalletBalanceFromApi(requestAddwalletbalance)
    }

    fun deduct_walletBalance(activity:CustomAppActivity,binding: FragmentPaymentGatewaytBinding,amt: String,number:String,type:String){

        this.activity=activity
        this.binding=binding
        sharedPreferencesUtil= SharedPreferencesUtil(activity)

        var requestAddwalletbalance=Request_deductWalletBalance()
        requestAddwalletbalance.amount=amt
        requestAddwalletbalance.paymentId=number
        requestAddwalletbalance.paymentType=type

        deductWalletBalanceFromApi(requestAddwalletbalance)
    }






   fun getPaytmTokenFromApi(param:Request_paytmChecksum){


   val call:Call<Response_Common> =
       sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
           apis.getPaytmToken(
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
                   Log.d("Token", commonResponse.toString());
               }else {

                   binding.progessBar.visibility= View.GONE
                   Log.e("Paytm",response.errorBody()!!.string())
               }
           }

           override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
               binding.progessBar.visibility= View.GONE
               activity.showToast(t.toString())
           }
       })
}

    fun addWalletBalanceFromApi(param: Request_addWalletBalance){

        binding.progessBar.visibility= View.VISIBLE
        val call:Call<Response_Common> =
            sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
                apis.addWalletBalance(
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
                    sharedPreferencesUtil.saveInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE,response.body()!!.getData()!!.result!!.walletamount)
                    response.body()!!.message?.let { showResponseDialog(it) }
                }else {

                    binding.progessBar.visibility= View.GONE
                    showResponseDialog(response.errorBody()!!.toString())
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


    fun deductWalletBalanceFromApi(param: Request_deductWalletBalance){

        binding.progessBar.visibility= View.VISIBLE
        val call:Call<Response_Common> =
            sharedPreferencesUtil.getString(Constant.sharedPrefrencesConstant.X_TOKEN)?.let {
                apis.deductWalletBalance(
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
                    sharedPreferencesUtil.saveInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE+Constant.sharedPrefrencesConstant.REFFERAL_AMOUNT,response.body()!!.getData()!!.result!!.walletamount)
                    response.body()!!.message?.let { showResponseDialog(it) }
                }else {
                    binding.progessBar.visibility= View.GONE
                    Log.e("Home_Fragment_Lottery_Response",response.errorBody()!!.string())
                    showResponseDialog(response.errorBody()!!.toString())
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
                binding.progessBar.visibility= View.GONE
                activity.showToast(t.toString())
            }
        })
    }

    fun showResponseDialog(type: String){

        var dialog = Dialog(activity);
        dialog.setCancelable(false);

        var view = activity.layoutInflater.inflate(R.layout.toast_dialog, null)



        dialog.setContentView(view);
        if (dialog.getWindow() != null) {
            dialog.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        var btnOk = view.findViewById<Button>(R.id.dialog_response_ok)
        var response_txt = view.findViewById<TextView>(R.id.response_txt)

        response_txt.setText(type)

        btnOk.setOnClickListener(View.OnClickListener { view ->
            activity.onBackPressed()
            dialog.dismiss()
        })
        dialog.show();
    }

}


