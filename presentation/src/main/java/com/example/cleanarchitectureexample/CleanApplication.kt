package com.example.cleanarchitectureexample

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CleanApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            // TODO: 백그라운드 상태
        }
    }

    companion object {
        private var instance: CleanApplication? = null
        lateinit var appContext: Context
        val globalApplicationContext: CleanApplication
            get() {
                if (instance == null) {
                    throw IllegalStateException("This Application does not inherit GlobalApplication")
                }

                return instance as CleanApplication
            }
    }
}