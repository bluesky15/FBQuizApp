package com.lkb.fbquizapp

import android.app.Application
import android.content.Context

class FBQApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        fun getContext(): Context {
            return this.getContext()
        }
    }

}