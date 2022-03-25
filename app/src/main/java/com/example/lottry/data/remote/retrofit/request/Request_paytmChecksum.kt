package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_paytmChecksum {

    @SerializedName("orderId")
    var orderId:String=""

    @SerializedName("amount")
    var amount:String=""

    @SerializedName("callbackUrl")
     var callbackUrl:String=""

}