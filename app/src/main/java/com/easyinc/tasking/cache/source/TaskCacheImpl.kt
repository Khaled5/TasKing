package com.easyinc.tasking.cache.source

import com.easyinc.tasking.cache.database.dao.TaskDao
import com.easyinc.tasking.cache.entity.Task
import com.easyinc.tasking.data.source.TaskCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TaskCacheImpl @Inject constructor(private val taskDao: TaskDao):
    TaskCache {

    override fun getTasks(dayId: Long): Flowable<List<Task>> {
        return taskDao.getTasks(dayId)
    }

    override fun getAllTasks(): Flowable<List<Task>> {
        return taskDao.getAllTasks()
    }

    override fun insertNewTask(task: Task): Completable {
        return Completable.defer {
            taskDao.insertTask(task)
            Completable.complete()
        }
    }

    override fun deleteAll(): Completable {
        return Completable.defer {
            taskDao.deleteAll()
            Completable.complete()
        }
    }

    override fun isDataEmpty(dayId: Long): Single<Boolean> {
        return Single.defer {
            taskDao.getTasks(dayId).isEmpty
        }
    }

    override fun deleteTask(task: Task): Completable {
        return Completable.defer {
            taskDao.deleteTask(task)
            Completable.complete()
        }
    }

    override fun updateTask(task: Task): Completable {
        return Completable.defer {
            taskDao.updateTask(task)
            Completable.complete()
        }
    }
}