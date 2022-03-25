package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FakeUser {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

}