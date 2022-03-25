package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class User {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("profilePic")
    @Expose
    var profilePic: Any? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

}
