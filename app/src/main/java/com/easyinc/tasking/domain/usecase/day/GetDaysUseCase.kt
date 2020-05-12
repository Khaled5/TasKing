package com.easyinc.tasking.domain.usecase.day

import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.data.repository.DaysRepository
import com.easyinc.tasking.domain.day.repository.IDaysRepository
import com.easyinc.tasking.domain.executor.PostExecutionThread
import com.easyinc.tasking.domain.executor.ThreadExecutor
import com.easyinc.tasking.domain.usecase.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

open class GetDaysUseCase
@Inject constructor(
    private val daysRepository: IDaysRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<List<Day>, Void>(threadExecutor,postExecutionThread) {


    override fun buildUseCaseObservable(params: Void?): Flowable<List<Day>> {
        return daysRepository.getDays()
    }

}