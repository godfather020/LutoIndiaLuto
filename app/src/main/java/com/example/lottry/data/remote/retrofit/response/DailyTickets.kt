package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DailyTickets {

    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("rows")
    @Expose
    var rows: List<Row>? = null
}
