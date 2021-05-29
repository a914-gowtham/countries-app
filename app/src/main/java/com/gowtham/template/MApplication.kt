package com.gowtham.template

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

    }
}