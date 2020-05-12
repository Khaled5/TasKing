package com.easyinc.tasking.common.utils

import android.util.Log

object Logger {

    private const val TAG = "EasyApp"

    fun dt(value: String) {
        Log.d(TAG, "Thread Name: ${Thread.currentThread().name} - $value")
    }
}