package com.lkb.fbquizapp

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class FBQApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }

    companion object {
        fun getContext(): Context {
            return this.getContext()
        }
    }

}