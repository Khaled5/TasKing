package com.easyinc.tasking.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.cache.entity.Task
import com.easyinc.tasking.common.base.BaseViewModel
import com.easyinc.tasking.data.model.DayModel
import com.easyinc.tasking.common.utils.Resource
import com.easyinc.tasking.data.mapper.MapDay
import com.easyinc.tasking.data.mapper.MapList
import com.easyinc.tasking.domain.usecase.day.AddDayUseCase
import com.easyinc.tasking.domain.usecase.day.DeleteDaysUseCase
import com.easyinc.tasking.domain.usecase.day.GetDaysUseCase
import com.easyinc.tasking.domain.usecase.task.GetAllTasksUseCase
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val getDaysUseCase: GetDaysUseCase,
    private val addDayUseCase: AddDayUseCase,
    private val deleteDaysUseCase: DeleteDaysUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
)
    : BaseViewModel() {

    private val daysLiveData = MutableLiveData<Resource<List<DayModel>>>()
    private val percentLiveData = MutableLiveData<String>()
    private var mTasksList = mutableListOf<Task>()

    fun insertDay(day: DayModel){
        addDayUseCase.execute(MapDay().mapFrom(day)).subscribe()
    }

    fun deleteAll(){
        deleteDaysUseCase.execute().subscribe()
    }

    fun getDays() {
        getAllTasks()
        compositeDisposable.add(getDaysUseCase.execute().map { list ->
            MapList(MapDay()).mapTo(list).map {
                 it.copy(progressPercent = getPercentById(it.id))
            }
        }.subscribe(
            {
                daysLiveData.postValue(Resource.Success(it))},
            {daysLiveData.postValue(Resource.Error(it.message!!))}
        ))
    }

    fun getAllTasks(){
        compositeDisposable.add(getAllTasksUseCase.execute().subscribe(
            {
                if (mTasksList.size > 0){
                    mTasksList.removeAll { true }
                    mTasksList.addAll(it)
                }else {
                    mTasksList.addAll(it)
                    percentLiveData.postValue(calculate(mTasksList.size, mTasksList.filter { it.isDone }.size))
                }
            },
            {
                daysLiveData.postValue(Resource.Error(it.message!!))
            }
        ))
    }

    fun observeDaysList(): LiveData<Resource<List<DayModel>>>{
        return daysLiveData
    }

    fun getProgressPercent(): LiveData<String>{
        percentLiveData.postValue(calculate(mTasksList.size, mTasksList.filter { it.isDone }.size))
        return percentLiveData
    }

    private fun getPercentById(dayId: Long): String{
        val tasksList = mTasksList.filter { task -> task.dayId == dayId }
        return calculate(tasksList.size, tasksList.filter { it.isDone }.size)
    }

    private fun calculate(all: Int, score: Int): String{
        if (all == 0)
            return "0%"
        return (((score.toFloat() / all.toFloat()) * 100).toInt()).toString()+"%"
    }

}