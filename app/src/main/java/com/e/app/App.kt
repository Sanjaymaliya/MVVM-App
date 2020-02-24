package com.e.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.e.app.koin.networkModule
import com.e.app.koin.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application(), Application.ActivityLifecycleCallbacks {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: App

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val listOfModule = arrayListOf(networkModule, viewModule)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOfModule)
        }

        registerActivityLifecycleCallbacks(this)

    }
    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}
