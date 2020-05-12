package com.easyinc.tasking.di.module

import com.easyinc.tasking.cache.source.TaskCacheImpl
import com.easyinc.tasking.data.repository.TasksRepository
import com.easyinc.tasking.data.source.TaskCache
import com.easyinc.tasking.data.source.TasksCacheDataStore
import com.easyinc.tasking.data.source.TasksDataStore
import com.easyinc.tasking.di.scope.FragmentScope
import com.easyinc.tasking.domain.task.repository.ITasksRepository
import dagger.Binds
import dagger.Module

@Module
abstract class TaskModule {

    @FragmentScope
    @Binds
    abstract fun provideTasksCache(tasksCacheImpl: TaskCacheImpl): TaskCache

    @FragmentScope
    @Binds
    abstract fun provideTasksDataStore(tasksCacheDataStore: TasksCacheDataStore): TasksDataStore

    @FragmentScope
    @Binds
    abstract fun provideTasksRepository(tasksRepository: TasksRepository): ITasksRepository

}