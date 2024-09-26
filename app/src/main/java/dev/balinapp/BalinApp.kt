package dev.balinapp

import android.app.Application
import dev.balinapp.di.AppComponent
import dev.balinapp.di.DaggerAppComponent

class BalinApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }

    fun getAppComponent() = appComponent
}