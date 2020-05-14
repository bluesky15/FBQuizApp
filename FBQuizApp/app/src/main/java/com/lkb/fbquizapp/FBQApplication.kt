package com.lkb.fbquizapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.lkb.fbquizapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FBQApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        startKoin {
            androidLogger()
            androidContext(this@FBQApplication)
            modules(appModule)
        }
    }
}