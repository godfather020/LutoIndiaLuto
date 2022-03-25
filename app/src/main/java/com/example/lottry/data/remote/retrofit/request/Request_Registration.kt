package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_Registration {

    @SerializedName("phoneNumber")
    var phoneNumber:String=""

    @SerializedName("userName")
    var userName:String=""

    @SerializedName("promocode")
    var refferalCode:String=""

}