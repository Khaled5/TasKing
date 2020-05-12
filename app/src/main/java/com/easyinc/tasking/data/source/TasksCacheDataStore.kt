package com.easyinc.tasking.data.source

import com.easyinc.tasking.cache.entity.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TasksCacheDataStore
@Inject constructor(
    private val taskCache: TaskCache
): TasksDataStore {
    override fun getTasks(dayId: Long): Flowable<List<Task>> {
        return taskCache.getTasks(dayId)
    }

    override fun getAllTasks(): Flowable<List<Task>> {
        return taskCache.getAllTasks()
    }

    override fun insertNewTask(task: Task): Completable {
        return taskCache.insertNewTask(task)
    }

    override fun deleteAll(): Completable {
        return taskCache.deleteAll()
    }

    override fun isDataEmpty(dayId: Long): Single<Boolean> {
        return taskCache.getTasks(dayId).isEmpty
    }

    override fun deleteTask(task: Task): Completable {
        return taskCache.deleteTask(task)
    }

    override fun updateTask(task: Task): Completable {
        return taskCache.updateTask(task)
    }
}