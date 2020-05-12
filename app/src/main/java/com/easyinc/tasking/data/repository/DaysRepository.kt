package com.easyinc.tasking.data.repository

import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.data.source.DaysCacheDataStore
import com.easyinc.tasking.data.source.DaysDataStore
import com.easyinc.tasking.domain.day.repository.IDaysRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class DaysRepository @Inject constructor(private val daysDataStore: DaysDataStore
):
    IDaysRepository {


    override fun getDays(): Flowable<List<Day>> {
       return daysDataStore.isDataEmpty().flatMapPublisher {
            when(it){
                true -> {
                    Flowable.empty()
                }
                false -> {
                    daysDataStore.getDays()

                }
            }
        }

    }

    override fun insertNewDay(day: Day): Completable {
        return daysDataStore.insertNewDay(day)}

    override fun deleteAll() = daysDataStore.deleteAll()


}