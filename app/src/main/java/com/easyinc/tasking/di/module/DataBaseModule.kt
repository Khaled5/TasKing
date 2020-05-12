package com.easyinc.tasking.di.module

import com.easyinc.tasking.cache.database.TasksRoomDatabase
import com.easyinc.tasking.cache.database.dao.DayDao
import com.easyinc.tasking.cache.database.dao.TaskDao
import com.easyinc.tasking.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @FragmentScope
    @Provides
    fun provideDayDao(tasksRoomDatabase: TasksRoomDatabase): DayDao {
        return tasksRoomDatabase.dayDao()
    }


    @FragmentScope
    @Provides
    fun provideTaskDao(tasksRoomDatabase: TasksRoomDatabase): TaskDao {
        return tasksRoomDatabase.taskDao()
    }

}