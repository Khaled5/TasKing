package com.easyinc.tasking.domain.usecase.task

import com.easyinc.tasking.cache.entity.Task
import com.easyinc.tasking.data.repository.TasksRepository
import com.easyinc.tasking.domain.usecase.CompletableUseCase
import com.easyinc.tasking.domain.executor.PostExecutionThread
import com.easyinc.tasking.domain.executor.ThreadExecutor
import com.easyinc.tasking.domain.task.repository.ITasksRepository
import io.reactivex.Completable
import javax.inject.Inject

open class DeleteTaskUseCase
@Inject constructor(
    private val tasksRepository: ITasksRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<Task>(threadExecutor,postExecutionThread){

    override fun buildUseCaseObservable(params: Task?): Completable {
        return tasksRepository.deleteTask(params!!)
    }

}