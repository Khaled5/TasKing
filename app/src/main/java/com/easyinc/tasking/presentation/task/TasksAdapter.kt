package com.easyinc.tasking.presentation.task

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easyinc.tasking.R
import com.easyinc.tasking.common.extentions.inflate
import com.easyinc.tasking.data.model.TaskModel
import kotlinx.android.synthetic.main.task_item.view.*
import javax.inject.Inject

class TasksAdapter @Inject constructor() : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    private var tasksList = listOf<TaskModel>()
    private var itemsFinal: List<TaskModel> = listOf()

    internal var clickListener: (TaskModel) -> Unit = { _ -> }
    internal var checkboxListener: (TaskModel,CheckBox) -> Unit = { _,_ -> }
    internal var onLongClickListener: (TaskModel) -> Unit = {_-> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TasksViewHolder(parent.inflate(R.layout.task_item))

    override fun getItemCount() = itemsFinal.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) = holder.bind(itemsFinal[position],clickListener,checkboxListener,onLongClickListener)

    fun setList(list: List<TaskModel>){
        tasksList = list
        itemsFinal = tasksList
        notifyDataSetChanged()
    }

 inner class TasksViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
     fun bind(task: TaskModel, clickListener: (TaskModel) -> Unit, checkboxListener: (TaskModel,CheckBox) -> Unit, onLongClickListener: (TaskModel) -> Unit){
         itemView.task_item_title.isCheckBoxChecked(task.isDone)

         itemView.task_item_title.text = task.title
         itemView.task_item_checkBox.isChecked = task.isDone
         itemView.setOnClickListener { clickListener(task) }
         itemView.setOnLongClickListener {
             /*it.delete_task_checkBox.visibility = View.VISIBLE
             it.task_item_checkBox.visibility = View.GONE*/
             onLongClickListener(task)
             return@setOnLongClickListener true
         }
         itemView.task_item_checkBox.setOnClickListener {
             (it as CheckBox).isChecked = it.isChecked
             checkboxListener(task,it) }

         itemView.delete_task_checkBox.setOnClickListener {

         }
     }

 }

    private fun TextView.isCheckBoxChecked(isChecked: Boolean){
        if (isChecked){
            this.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            this.setTextColor(Color.parseColor("#636363"))
        } else{
            this.paintFlags = Paint.HINTING_ON
            this.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    fun filterTasksByKeyword(keyword: String?){
        if (keyword!!.isEmpty())
            itemsFinal = tasksList
        else
            itemsFinal = tasksList.filter { it.title?.toLowerCase()!!.contains(keyword) || it.description?.toLowerCase()!!.contains(keyword) }

        notifyDataSetChanged()
    }

    fun filterDoneTasks(isDone: Boolean){
        if (isDone)
            itemsFinal = tasksList.filter { it.isDone }
        else
            itemsFinal = tasksList.filter { !it.isDone }

        notifyDataSetChanged()
    }

}