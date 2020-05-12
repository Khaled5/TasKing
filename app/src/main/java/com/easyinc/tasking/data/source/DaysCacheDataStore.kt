package com.easyinc.tasking.data.source

import com.easyinc.tasking.cache.entity.Day
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class DaysCacheDataStore @Inject constructor(
    private val dayCache: DayCache
): DaysDataStore {
    override fun getDays(): Flowable<List<Day>> {
        return dayCache.getDays()
    }

    override fun insertNewDay(day: Day): Completable {
        return dayCache.insertNewDay(day)
    }

    override fun deleteAll(): Completable {
        return dayCache.deleteAll()
    }

    override fun isDataEmpty(): Single<Boolean> {
        return dayCache.isDataEmpty()
    }

}