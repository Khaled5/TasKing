package com.easyinc.tasking.di.module

import android.app.Application
import com.easyinc.tasking.cache.database.TasksRoomDatabase
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("date")
    fun provideDate(): String{
        return SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date())
    }

    @Provides
    @Singleton
    @Named("day_id")
    fun provideDayId(): Long{
        return SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date()).toLong()
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): TasksRoomDatabase{
        return TasksRoomDatabase.getDatabase(application.applicationContext)
    }

}