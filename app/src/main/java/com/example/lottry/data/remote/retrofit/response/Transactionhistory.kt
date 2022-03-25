package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Transactionhistory {

    @SerializedName("count")
    @Expose
     val count: Int? = null

    @SerializedName("rows")
    @Expose
     val rows: List<Row>? = null
}