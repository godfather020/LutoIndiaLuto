package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Ticket {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("ticketNumber")
    @Expose
    var ticketNumber: String? = null

}
