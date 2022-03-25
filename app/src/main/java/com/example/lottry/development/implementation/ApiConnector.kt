package com.example.lottry.development.implementation

import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.development.interfaces.ApiConnect
import retrofit2.Retrofit

class ApiConnector(val retrofit: Retrofit) :ApiConnect {

    override val api: Apis
        get() = retrofit.create(api::class.java)
}