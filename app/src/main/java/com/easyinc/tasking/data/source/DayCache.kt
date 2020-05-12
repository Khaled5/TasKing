package com.easyinc.tasking.data.source

import com.easyinc.tasking.cache.entity.Day
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface DayCache {

    fun getDays(): Flowable<List<Day>>

    fun insertNewDay(day: Day): Completable

    fun deleteAll(): Completable

    fun isDataEmpty(): Single<Boolean>

}