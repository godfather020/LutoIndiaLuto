package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_buyTicket {

    @SerializedName("lotteryId")
    var lotteryId:String=""

    @SerializedName("ticketList")
    var ticketList:ArrayList<String>?=null

    @SerializedName("ticketPrice")
     var ticketPrice:String=""



}