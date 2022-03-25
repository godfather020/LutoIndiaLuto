package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_deductWalletBalance {

    @SerializedName("amount")
    var amount:String=""

    @SerializedName("paymentType")
    var paymentType:String=""

    @SerializedName("paymentId")
    var paymentId:String=""

}