package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable






class Row : Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("winningAmount")
    @Expose
    var winningAmount: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("jackpotAmount")
    @Expose
    var jackpotAmount: String? = null

    @SerializedName("startTime")
    @Expose
    var startTime: String? = null

    @SerializedName("openTime")
    @Expose
    var openTime: String? = null

    @SerializedName("endTime")
    @Expose
    var endTime: String? = null

    @SerializedName("ticketPrice")
    @Expose
    var ticketPrice: String? = null

    @SerializedName("maxTicket")
    @Expose
    var maxTicket: Int? = null

    @SerializedName("ticketType")
    @Expose
    var ticketType: String? = null



    @SerializedName("ticketNumber")
    @Expose
    var ticketNumber: String? = null



    @SerializedName("Lottery")
    @Expose
    var lottery: Lottery? = null

    @SerializedName("User")
    @Expose
    var user: User? = null

    @SerializedName("fake_user")
    @Expose
    var fakeUser: FakeUser? = null

    @SerializedName("Ticket")
    @Expose
    var ticket: Ticket? = null

    //transication history

    @SerializedName("transactionId")
    @Expose
     val transactionId: String? = null

    @SerializedName("amount")
    @Expose
     val amount: Int? = null

    @SerializedName("type")
    @Expose
     val type: String? = null

    @SerializedName("status")
    @Expose
     val status: String? = null

    @SerializedName("remark")
    @Expose
     val remark: String? = null

    //notification


    @SerializedName("title")
    @Expose
     val title: String? = null






}