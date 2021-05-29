package com.gowtham.template.utils

import android.util.Log
import com.gowtham.template.BuildConfig.DEBUG

object LogMessage {

    private val logVisible = DEBUG

    internal fun v(msg: String) {
        if (logVisible)
            Log.v("Countries-App",msg)
    }

    internal fun e(msg: String) {
        if (logVisible)
            Log.e("Countries-App",msg)
    }

}