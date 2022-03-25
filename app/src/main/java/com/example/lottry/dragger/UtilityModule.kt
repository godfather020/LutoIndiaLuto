package com.example.lottry.dragger

import android.app.Application
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtilInterface
import com.example.lottry.utils.shared_prefrence.SharedPrefrenceUtilImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilityModule {

    @Provides
    @Singleton
    internal fun provideSharedPrefrece(application: Application):SharedPreferencesUtil =
        SharedPreferencesUtil(application)

}