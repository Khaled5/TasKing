package com.easyinc.tasking.di.module

import com.easyinc.tasking.di.scope.FragmentScope
import com.easyinc.tasking.presentation.home.HomeFragment
import com.easyinc.tasking.presentation.task.TaskFragment
import com.easyinc.tasking.presentation.task.TasksFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [DayModule::class,TaskModule::class,DataBaseModule::class])
    abstract fun provideMainFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TaskModule::class,DataBaseModule::class])
    abstract fun provideTasksFragment(): TasksFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TaskModule::class,DataBaseModule::class])
    abstract fun provideTaskFragment(): TaskFragment

}