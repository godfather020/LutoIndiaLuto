package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Body {

    @SerializedName("resultInfo")
    @Expose
     val resultInfo: ResultInfo? = null

    @SerializedName("txnToken")
    @Expose
     val txnToken: String? = null

    @SerializedName("isPromoCodeValid")
    @Expose
     val isPromoCodeValid: Boolean? = null

    @SerializedName("authenticated")
    @Expose
     val authenticated: Boolean? = null
}
