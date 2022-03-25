package com.example.lottry.application

import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.lottry.dragger.*
import com.example.lottry.ui.activity.login.LoginActivity

class WikiApplication :Application() {



    companion object {
        var instance: WikiApplication? = null
            private set
        var component: AppComponent? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance=this
        component=initDrager(this)
    }

    private fun initDrager(wikiApplication: WikiApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(wikiApplication))
            .networkModule(NetworkModule())
            .primitivesModule(PrimitivesModule())
            .utilityModule(UtilityModule())
            .build()


}
