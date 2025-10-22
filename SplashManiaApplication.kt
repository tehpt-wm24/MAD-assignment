package com.example.splashmaniaapp

import android.app.Application
import com.example.splashmaniaapp.di.AppContainer
import com.example.splashmaniaapp.di.AppDataContainer

class SplashManiaApplication : Application() {
    val container: AppContainer by lazy {
        AppDataContainer(applicationContext)
    }
}