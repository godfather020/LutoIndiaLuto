package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose




class Response_Data {

    @SerializedName("otp")
    var otp:String=""

    @SerializedName("result")
    @Expose
   var result: Result? = null

    @SerializedName("token")
    @Expose
    var token: String = ""

    @SerializedName("id")
    @Expose
    var id: String = ""

    @SerializedName("userName")
    @Expose
    var userName: String = ""

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String = ""

    @SerializedName("lotteries")
    @Expose
    var lotteries: Lotteries? = null

    @SerializedName("ticketList")
    @Expose
    var ticketList: List<Response_Ticket_List>? = null

    @SerializedName("monthlyTickets")
    @Expose
    var monthlyTickets: DailyTickets? = null

    @SerializedName("dailyTickets")
    @Expose
    var dailyTickets: DailyTickets? = null

    @SerializedName("occasionallyTickets")
    @Expose
    var occasionallyTickets: DailyTickets? = null

    @SerializedName("winners")
    @Expose
    var winners: Winners? = null

 @SerializedName("response")
 @Expose
 var response: Response? = null

 @SerializedName("transactions")
 @Expose
 var transactions: Transactionhistory? = null

 @SerializedName("notifications")
 @Expose
 var notifications: Notification? = null

}