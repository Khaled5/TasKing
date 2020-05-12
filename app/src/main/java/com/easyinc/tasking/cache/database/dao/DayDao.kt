package com.easyinc.tasking.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easyinc.tasking.cache.entity.Day
import io.reactivex.Flowable

@Dao
interface DayDao {

    @Query("SELECT * from day_table ORDER BY id ASC")
    fun getDays(): Flowable<List<Day>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDay(day: Day)

    @Query("DELETE FROM day_table")
    fun deleteAll()

}