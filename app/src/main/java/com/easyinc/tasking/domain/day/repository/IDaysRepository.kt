package com.easyinc.tasking.domain.day.repository

import com.easyinc.tasking.cache.entity.Day
import io.reactivex.Completable
import io.reactivex.Flowable

interface IDaysRepository {

    fun getDays(): Flowable<List<Day>>

    fun insertNewDay(day: Day): Completable

    fun deleteAll(): Completable

}