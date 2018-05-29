package com.melo.allan.poc

import android.app.Application
import android.util.Log

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.liferay.mobile.screens.context.LiferayScreensContext

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by allan.melo on 11/05/18.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            LiferayScreensContext.init(this)
            retrofit = Retrofit.Builder()
                    .baseUrl(getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        } catch (e: Exception) {
            Log.e("ERRO", e.localizedMessage)
        }
    }

    companion object {
        var retrofit: Retrofit? = null
            private set
    }
}