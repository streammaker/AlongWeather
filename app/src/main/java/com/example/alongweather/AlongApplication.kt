package com.example.alongweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AlongApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context

        const val TOKEN = "BpNP8N98RfhmXKTH"
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}