package com.easyinc.tasking.presentation.task

import androidx.lifecycle.MutableLiveData
import com.easyinc.tasking.common.base.BaseViewModel
import com.easyinc.tasking.data.model.TaskModel
import com.easyinc.tasking.common.utils.Resource
import com.easyinc.tasking.data.mapper.MapList
import com.easyinc.tasking.data.mapper.MapTask
import com.easyinc.tasking.domain.usecase.task.*
import javax.inject.Inject

class TasksViewModel
@Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase
): BaseViewModel() {

    val tasksListLiveData = MutableLiveData<Resource<List<TaskModel>>>()

    fun observeTasks(dayId: Long?){
            compositeDisposable.add(
                getTasksUseCase.execute(dayId).subscribe(
                    {
                        tasksListLiveData.postValue(Resource.Success(
                            MapList(MapTask()).mapTo(it)
                        ))
                    },
                    {
                        tasksListLiveData.postValue(Resource.Error(it.message!!))
                    }
                )
            )
    }

    fun insertTask(task: TaskModel){
        addTaskUseCase.execute(
            MapTask().mapFrom(task)
        ).subscribe()
    }

    fun updateTask(task: TaskModel){
        updateTaskUseCase.execute(
            MapTask().mapFrom(task)
        ).subscribe()
    }

    fun deleteTask(task: TaskModel){
        deleteTaskUseCase.execute(
            MapTask().mapFrom(task)
        ).subscribe()
    }

    fun deleteAllTask(){
        deleteAllTaskUseCase.execute().subscribe()
    }

}