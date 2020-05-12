package com.easyinc.tasking.data.mapper

import com.easyinc.tasking.cache.entity.Task
import com.easyinc.tasking.data.model.TaskModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapTask @Inject constructor(): Mapper<Task,TaskModel> {

    override fun mapTo(input: Task): TaskModel {
        return TaskModel(input.title,input.description,input.dayId,input.reminder,input.isDone,input.id)
    }

    override fun mapFrom(input: TaskModel): Task {
        return Task(input.title,input.description,input.dayId,input.reminder,input.isDone,input.id)
    }
}