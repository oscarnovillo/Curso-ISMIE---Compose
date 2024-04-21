package com.example.primerxmlmvvm

import android.app.Application

import timber.log.Timber



class SumarApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }
}