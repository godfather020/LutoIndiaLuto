package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Response_Ticket_List : Serializable {

    @SerializedName("id")
    var id:String = ""

    @SerializedName("isSelected")
     var selected:Boolean = false

    constructor(id:String,selected:Boolean){
        this.id=id
        this.selected=selected

    }

}