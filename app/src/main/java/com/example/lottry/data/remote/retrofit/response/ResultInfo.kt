package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultInfo {

    @SerializedName("resultStatus")
    @Expose
    private val resultStatus: String? = null

    @SerializedName("resultCode")
    @Expose
    private val resultCode: String? = null

    @SerializedName("resultMsg")
    @Expose
    private val resultMsg: String? = null
}
