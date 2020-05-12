package com.easyinc.tasking.cache.source

import com.easyinc.tasking.cache.database.dao.DayDao
import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.common.utils.Logger
import com.easyinc.tasking.data.source.DayCache
import com.easyinc.tasking.data.source.DaysDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DayCacheImpl @Inject constructor(
    private val dayDao: DayDao
): DayCache {
    override fun getDays(): Flowable<List<Day>> {
        return dayDao.getDays()
    }

    override fun insertNewDay(day: Day): Completable {
        return Completable.defer {
            dayDao.insertDay(day)
            Completable.complete()
        }
    }

    override fun deleteAll(): Completable {
        return Completable.defer {
            dayDao.deleteAll()
            Completable.complete()
        }
    }

    override fun isDataEmpty(): Single<Boolean> {
        return Single.defer {
            dayDao.getDays().isEmpty
        }
    }
}