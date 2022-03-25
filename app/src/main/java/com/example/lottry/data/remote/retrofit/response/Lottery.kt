package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Lottery {


    @SerializedName("id")
    @Expose
   var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("ticketType")
    @Expose
    var ticketType: String? = null

    @SerializedName("jackpotAmount")
    @Expose
    var jackpotAmount: String? = null

    @SerializedName("openTime")
    @Expose
    var openTime: String? = null

}
