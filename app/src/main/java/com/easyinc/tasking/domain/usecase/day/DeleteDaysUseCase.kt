package com.easyinc.tasking.domain.usecase.day

import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.data.repository.DaysRepository
import com.easyinc.tasking.domain.day.repository.IDaysRepository
import com.easyinc.tasking.domain.usecase.CompletableUseCase
import com.easyinc.tasking.domain.executor.PostExecutionThread
import com.easyinc.tasking.domain.executor.ThreadExecutor
import io.reactivex.Completable
import javax.inject.Inject

open class DeleteDaysUseCase
@Inject constructor(
    private val daysRepository: IDaysRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<Day>(threadExecutor,postExecutionThread){

    override fun buildUseCaseObservable(params: Day?): Completable {
        return daysRepository.deleteAll()
    }

}