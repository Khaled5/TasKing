package com.easyinc.tasking

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.multidex.MultiDex
import com.easyinc.tasking.di.DaggerMainComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMainComponent.builder().application(this).build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        val channel1 = NotificationChannel("CHANNEL_1_ID","Channel 1", NotificationManager.IMPORTANCE_HIGH)
        channel1.description = "This is channel 1"
        channel1.lockscreenVisibility = NotificationCompat.PRIORITY_HIGH
        channel1.enableVibration(true)
        channel1.importance = NotificationManager.IMPORTANCE_HIGH
    }
}