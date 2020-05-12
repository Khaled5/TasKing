package com.easyinc.tasking.cache.database.dao

import androidx.room.*
import com.easyinc.tasking.cache.entity.Task
import io.reactivex.Flowable

@Dao
interface TaskDao {

    @Query("SELECT * from task_table where day_id = :dayId ORDER BY id DESC")
    fun getTasks(dayId: Long?): Flowable<List<Task>>

    @Query("SELECT * from task_table ORDER BY id DESC")
    fun getAllTasks(): Flowable<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: Task)

    @Query("DELETE FROM task_table")
    fun deleteAll()

}