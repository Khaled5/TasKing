package com.easyinc.tasking.data.source

import com.easyinc.tasking.cache.entity.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TasksDataStore {

    fun getTasks(dayId: Long): Flowable<List<Task>>

    fun getAllTasks(): Flowable<List<Task>>

    fun insertNewTask(task: Task): Completable

    fun deleteAll(): Completable

    fun isDataEmpty(dayId: Long): Single<Boolean>

    fun deleteTask(task: Task): Completable

    fun updateTask(task: Task): Completable

}