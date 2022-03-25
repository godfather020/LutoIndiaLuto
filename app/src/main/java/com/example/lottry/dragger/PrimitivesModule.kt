package com.example.lottry.dragger

import com.example.lottry.BuildConfig
import com.example.lottry.utils.Constant
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PrimitivesModule {

    @Provides
    @Named(Constant.url.API_DEVELOPMENT_URL)
    fun provideDevelopmentURL(): String = BuildConfig.URL_DEVELOPMENT

    @Provides
    @Named(Constant.url.API_LIVE_URL)
    fun provideLiveURL(): String = BuildConfig.URL_LIVE

    @Provides
    @Named(Constant.url.API_TESTING_URL)
    fun provideTestingURL(): String = BuildConfig.URL_TESTING
}