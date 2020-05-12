package com.easyinc.tasking.presentation.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.easyinc.tasking.R
import com.easyinc.tasking.common.base.BaseFragment
import com.easyinc.tasking.common.extentions.androidLazy
import com.easyinc.tasking.common.extentions.replaceAnimatedFragment
import com.easyinc.tasking.common.extentions.snacks
import com.easyinc.tasking.data.model.TaskModel
import com.easyinc.tasking.common.utils.FragmentNullableArgumentDelegate
import com.easyinc.tasking.common.utils.Logger
import com.easyinc.tasking.common.utils.Resource
import com.easyinc.tasking.common.viewmodel.ViewModelFactory
import com.easyinc.tasking.common.viewmodel.getViewModel
import kotlinx.android.synthetic.main.delete_task_dialog.view.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.android.synthetic.main.tasks_toolbar.view.*
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty


class TasksFragment : BaseFragment() {

    var dayId: Long? by argumentNullable()

    @Inject
    lateinit var tasksAdapter: TasksAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<TasksViewModel>
    private val tasksViewModel by androidLazy {
        getViewModel<TasksViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tasks, container, false)

        tasksViewModel.observeTasks(dayId)

        tasksViewModel.tasksListLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> tasksAdapter.setList(it.data!!)
                Resource.Status.ERROR -> mView.snacks(it.message!!)
            }

        })

        mView.tasks_recycler.adapter = tasksAdapter
        mView.tasks_recycler.setHasFixedSize(true)
        mView.tasks_recycler.layoutManager = LinearLayoutManager(context)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClick(view)
        searchViewOperation(view)
        filterTasks(view)

    }


    fun searchViewOperation(sView: View){


        sView.search_task.setOnClickListener {
            sView.search_task_card.visibility = View.VISIBLE
        }

        sView.search_task_key_word.setOnCloseListener {
            sView.search_task_card.visibility = View.GONE
            return@setOnCloseListener true
        }

        sView.search_task_key_word.setOnQueryTextFocusChangeListener { _, b ->
            if (b)
                sView.search_task_card.visibility = View.VISIBLE

            else
                sView.search_task_card.visibility = View.GONE
        }

        sView.search_task_key_word.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                tasksAdapter.filterTasksByKeyword(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tasksAdapter.filterTasksByKeyword(newText)
                return false
            }
        })
    }

    fun filterTasks(sView: View){
        sView.filter_list_task.setOnClickListener {
            val popupMenu = PopupMenu(context!!,sView.filter_list_task)
            popupMenu.menuInflater.inflate(R.menu.tasks_menu,popupMenu.menu)


            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.done -> tasksAdapter.filterDoneTasks(true)
                    R.id.non_done -> tasksAdapter.filterDoneTasks(false)
                }

                return@setOnMenuItemClickListener true
            }

            popupMenu.show()
        }
    }

    fun initOnClick(view: View){

        view.back_task_list.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        view.add_task.setOnClickListener {
            val taskFragment = TaskFragment.newInstance(dayId = dayId)
            replaceAnimatedFragment(R.id.container, taskFragment,true)
        }

        tasksAdapter.clickListener = {
            val taskFragment = TaskFragment.newInstance(it,dayId)
            replaceAnimatedFragment(R.id.container, taskFragment,true)
        }

        tasksAdapter.checkboxListener = {t , v ->
            val doneTask = t.copy(isDone = v.isChecked)
            tasksViewModel.updateTask(doneTask)
        }

        tasksAdapter.onLongClickListener = {task ->
            val dialogBuilder  = android.app.AlertDialog.Builder(context)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.delete_task_dialog, null)
            dialogBuilder .setView(dialogView)

            val alertDialog = dialogBuilder.create()

            dialogView.delete_task.setOnClickListener {
                deleteTask(task)
                tasksAdapter.notifyDataSetChanged()
                alertDialog.dismiss()
            }

            alertDialog.window?.setBackgroundDrawableResource(R.color.colorSecondary)
            alertDialog.show()
            Logger.dt("tasks to delete: $task")
        }
    }

    private fun deleteTask(task: TaskModel){
        tasksViewModel.deleteTask(task)
    }

    companion object {
        fun newInstance(dayId: Long): TasksFragment =
            TasksFragment().apply {
                this.dayId = dayId
            }
    }

    fun <T : Any> argumentNullable(): ReadWriteProperty<Fragment, T?> =
        FragmentNullableArgumentDelegate()

}
