package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("id")
    @Expose
    var id: String = ""

    @SerializedName("userName")
    @Expose
    var userName: String = ""

    @SerializedName("refferalcode")
    @Expose
    var refferalcode : String = ""

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String = ""

    @SerializedName("profilePic")
    @Expose
    var profilePic: String = ""

    @SerializedName("wallet")
    @Expose
    var wallet: Int = 0

    @SerializedName("refferalamount")
    @Expose
    var refferalamount: Int = 0

    @SerializedName("walletamount")
    @Expose
    var walletamount: Int = 0
}