package com.jellypump.creativefood

import com.jellypump.creativefood.di.components.DaggerAppComponent
import dagger.android.support.DaggerApplication
import timber.log.Timber

class AndroidApplication: DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}