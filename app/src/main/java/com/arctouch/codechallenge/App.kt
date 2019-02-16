package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.di.networkModule
import com.arctouch.codechallenge.di.viewModelModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    companion object {

        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(networkModule, viewModelModule))
        Timber.plant(Timber.DebugTree())
        instance = this
    }
}