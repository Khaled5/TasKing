package com.easyinc.tasking.di.module

import com.easyinc.tasking.cache.source.DayCacheImpl
import com.easyinc.tasking.data.repository.DaysRepository
import com.easyinc.tasking.data.source.DayCache
import com.easyinc.tasking.data.source.DaysCacheDataStore
import com.easyinc.tasking.data.source.DaysDataStore
import com.easyinc.tasking.di.scope.FragmentScope
import com.easyinc.tasking.domain.day.repository.IDaysRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DayModule {

    @FragmentScope
    @Binds
    abstract fun provideDaysCache(dayCacheImpl: DayCacheImpl): DayCache

    @FragmentScope
    @Binds
    abstract fun provideDaysDataStore(daysCacheDataStore: DaysCacheDataStore): DaysDataStore

    @FragmentScope
    @Binds
    abstract fun provideDaysRepository(daysRepository: DaysRepository): IDaysRepository

}