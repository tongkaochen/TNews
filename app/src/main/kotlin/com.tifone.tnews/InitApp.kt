package com.tifone.tnews

import android.app.Application
import android.content.Context

class InitApp : Application() {
    companion object Constant{
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}