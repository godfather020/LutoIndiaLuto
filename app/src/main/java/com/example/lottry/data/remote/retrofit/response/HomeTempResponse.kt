package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeTempResponse {
    @SerializedName("header")
    @Expose
    var header: String? = null

    @SerializedName("rows")
    @Expose
    var rows: Row? = null
}