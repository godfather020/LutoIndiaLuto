package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDetail {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("userName")
    @Expose
    var userName: String = ""

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String = ""

    @SerializedName("profilePic")
    @Expose
    var profilePic: String = ""
}