package com.easyinc.tasking.domain.task.repository

import com.easyinc.tasking.cache.entity.Task
import io.reactivex.Completable
import io.reactivex.Flowable

interface ITasksRepository {

    fun insertNewTask(task: Task): Completable

    fun getTasks(dayId: Long?): Flowable<List<Task>>

    fun getAllTasks(): Flowable<List<Task>>

    fun deleteTask(task: Task): Completable

    fun updateTask(task: Task): Completable

    fun deleteAllTasks(): Completable
}