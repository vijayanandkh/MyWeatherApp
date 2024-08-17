package com.example.myweatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyWeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // Any other initialization code
    }


}