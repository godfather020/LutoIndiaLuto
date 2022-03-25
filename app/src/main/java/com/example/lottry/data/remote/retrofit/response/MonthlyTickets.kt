package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class MonthlyTickets {

    @SerializedName("count")
    @Expose
    private val count: Int? = null

    @SerializedName("rows")
    @Expose
    private val rows: List<Row>? = null

}
