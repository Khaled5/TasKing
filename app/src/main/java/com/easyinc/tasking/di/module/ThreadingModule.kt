package com.easyinc.tasking.di.module

import com.easyinc.tasking.data.executor.JobExecutor
import com.easyinc.tasking.domain.executor.PostExecutionThread
import com.easyinc.tasking.domain.executor.ThreadExecutor
import com.easyinc.tasking.presentation.UiThread
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ThreadingModule {

    @Singleton
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @Singleton
    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

}