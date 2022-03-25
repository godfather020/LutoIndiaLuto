package com.example.lottry.dragger

import android.app.Application
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.development.implementation.ConnectionApiEndPoint
import com.example.lottry.development.interfaces.EndPoint
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtilInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {



    @Provides
    internal fun provideEndPoint(@Named(Constant.url.API_DEVELOPMENT_URL)URL:String):EndPoint =
    ConnectionApiEndPoint().setEndPoint(URL)

    @Provides
    internal fun provideRetrofit(endPoint: EndPoint): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(endPoint.getUrl())
            .client(OkHttpClient())
            .build()

    }

    @Provides
    internal fun providRetrofitApi(retrofit: Retrofit):Apis{

        return retrofit.create(Apis::class.java)
    }


}