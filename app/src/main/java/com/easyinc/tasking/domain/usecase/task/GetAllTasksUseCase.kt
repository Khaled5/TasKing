package com.easyinc.tasking.domain.usecase.task

import com.easyinc.tasking.cache.entity.Task
import com.easyinc.tasking.data.repository.TasksRepository
import com.easyinc.tasking.domain.executor.PostExecutionThread
import com.easyinc.tasking.domain.executor.ThreadExecutor
import com.easyinc.tasking.domain.task.repository.ITasksRepository
import com.easyinc.tasking.domain.usecase.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

open class GetAllTasksUseCase
@Inject constructor(
    private val tasksRepository: ITasksRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<List<Task>, Unit>(threadExecutor,postExecutionThread) {

    override fun buildUseCaseObservable(params: Unit?): Flowable<List<Task>> {
        return tasksRepository.getAllTasks()
    }

}