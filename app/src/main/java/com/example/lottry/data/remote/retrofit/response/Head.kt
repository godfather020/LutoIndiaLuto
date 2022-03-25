package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Head {

    @SerializedName("responseTimestamp")
    @Expose
     val responseTimestamp: String? = null

    @SerializedName("version")
    @Expose
     val version: String? = null

    @SerializedName("signature")
    @Expose
     val signature: String? = null

}
