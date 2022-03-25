package com.example.lottry.development.implementation

import com.example.lottry.development.interfaces.EndPoint
import java.lang.IllegalStateException

class ConnectionApiEndPoint : EndPoint{

    private var url =""
    override fun getUrl(): String {
        if(url.trim{it <=' '}.isEmpty())
            throw IllegalStateException("Url is not set")
        return url
    }

    override fun getName(): String = "Connect API endpoint"

    public fun setEndPoint(url:String):ConnectionApiEndPoint{

        this.url=url
        return this
    }
}