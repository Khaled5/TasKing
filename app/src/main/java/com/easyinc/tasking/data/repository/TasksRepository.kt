package com.easyinc.tasking.data.repository

import com.easyinc.tasking.cache.entity.Task
import com.easyinc.tasking.data.source.TasksCacheDataStore
import com.easyinc.tasking.data.source.TasksDataStore
import com.easyinc.tasking.domain.task.repository.ITasksRepository
import io.reactivex.Flowable
import javax.inject.Inject

class TasksRepository
@Inject constructor(
    private val tasksDataStore: TasksDataStore
):
    ITasksRepository {

    override fun getTasks(dayId: Long?): Flowable<List<Task>>{
        return tasksDataStore.isDataEmpty(dayId!!).flatMapPublisher {
            when(it){
                true -> Flowable.empty()
                false -> tasksDataStore.getTasks(dayId)
            }
        }
    }

    override fun getAllTasks(): Flowable<List<Task>>{
        return tasksDataStore.getAllTasks()

    }

    override fun insertNewTask(task: Task) = tasksDataStore.insertNewTask(task)

    override fun deleteTask(task: Task) = tasksDataStore.deleteTask(task)

    override fun updateTask(task: Task) = tasksDataStore.updateTask(task)

    override fun deleteAllTasks() = tasksDataStore.deleteAll()
}