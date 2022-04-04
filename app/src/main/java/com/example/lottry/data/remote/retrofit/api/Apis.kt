package com.example.lottry.data.remote.retrofit.api

import com.example.lottry.data.remote.retrofit.request.*
import com.example.lottry.data.remote.retrofit.response.Response_Common
import com.example.lottry.utils.Constant
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Apis {

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.LOGIN)
    fun getOtp(
    @Body body:Request_Login
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.VERIFY)
    fun getVerifyOtp(
        @Body body:Request_Verify
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.REGISTER)
    fun getRegistraion(
        @Body body:Request_Registration
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.LOTTERIES)
    fun getLotteryDetail(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body:Request_Lottery
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @GET(Constant.ApiConstant.TICKETS)
    fun getTickeList(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.BUY_TICKETS)
    fun buyTicket(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_buyTicket
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.MY_TICKETS)
    fun myTicket(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_myTicket
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.TOP_WINNERS)
    fun getTopWinners(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_topWinners
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @GET(Constant.ApiConstant.WALLET_BALANCE)
    fun getWalletBalance(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.PAYTM_TOKEN)
    fun getPaytmToken(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_paytmChecksum
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.TRANS_HISTORY)
    fun getTransHistory(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_transHistory
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.NOTIFICATION)
    fun getNotificaiton(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_notification
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.ADD_WALLET_BALANCE)
    fun addWalletBalance(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_addWalletBalance
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constant.ApiConstant.DEDUCT_WALLET_BALANCE)
    fun deductWalletBalance(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_deductWalletBalance
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @FormUrlEncoded
    @POST(Constant.ApiConstant.SET_PROFILE_IMAGE)
    fun setProfileImg(
        @Header (Constant.sharedPrefrencesConstant.X_TOKEN) x_tocken: String,
        @Part image: String
    ):Call<Response_Common>

}