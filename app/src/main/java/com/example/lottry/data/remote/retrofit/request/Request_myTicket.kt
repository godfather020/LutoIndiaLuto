package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_myTicket {

    @SerializedName("page")
    var page:Int=0

    @SerializedName("limit")
    var limit:Int=0

    @SerializedName("purchaseDate")
    var purchaseDate:String=""

}