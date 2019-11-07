package com.klimaatmobiel

import android.app.Application
import timber.log.Timber

class PusherApplication : Application() {
    // A base class that contains global application state for the entire app

    override fun onCreate() {
        super.onCreate()
        appContext = this
        // Setup timber
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var appContext: PusherApplication
            private set
    }

}