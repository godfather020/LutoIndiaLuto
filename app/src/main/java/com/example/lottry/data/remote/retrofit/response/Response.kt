package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Response {

    @SerializedName("head")
    @Expose
     val head: Head? = null

    @SerializedName("body")
    @Expose
     val body: Body? = null
}