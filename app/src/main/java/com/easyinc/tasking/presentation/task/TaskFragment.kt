package com.easyinc.tasking.presentation.task

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easyinc.tasking.R
import com.easyinc.tasking.common.base.BaseFragment
import com.easyinc.tasking.common.extentions.androidLazy
import com.easyinc.tasking.common.extentions.snacks
import com.easyinc.tasking.data.model.TaskModel
import com.easyinc.tasking.common.reminder.ReminderBroadCast
import com.easyinc.tasking.common.utils.FragmentNullableArgumentDelegate
import com.easyinc.tasking.common.viewmodel.ViewModelFactory
import com.easyinc.tasking.common.viewmodel.getViewModel
import kotlinx.android.synthetic.main.fragment_task.view.*
import kotlinx.android.synthetic.main.reminder_picker.view.*
import kotlinx.android.synthetic.main.task_editor_toolbar.view.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty

class TaskFragment : BaseFragment() {

    private var task: TaskModel? by argumentNullable()
    private var hasTask: Boolean? = null

    private var dayId: Long? by argumentNullable()

    private var reminderTime: Long = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<TasksViewModel>
    private val taskViewModel by androidLazy {
        getViewModel<TasksViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_task, container, false)

        hasTask = task != null
        if (hasTask as Boolean)
            setEditTaskValues(mView)


        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClick(view)

    }

    private fun setEditTaskValues(view: View){
        view.task_title.setText(task?.title)
        view.task_description.setText(task?.description)

        if (task?.reminder!!.isNotBlank() && task?.reminder!!.isNotEmpty() )
            view.reminder_txt.text = task?.reminder
    }

    private fun initOnClick(view: View){
        view.save_task.setOnClickListener {

            if (view.task_title.text.toString().trim().isEmpty())
                view.snacks("Task field cannot be empty.")
            else {
                if (hasTask as Boolean) {
                    val oTask = TaskModel(
                        view.task_title.text.toString(),
                        view.task_description.text.toString(),
                        dayId,
                        view.reminder_txt.text.toString(),
                        task!!.isDone,
                        task!!.id
                    )
                    updateTask(oTask)
                    replaceFragment()
                } else {
                    val nTask = TaskModel(
                        view.task_title.text.toString(),
                        view.task_description.text.toString(),
                        dayId,
                        view.reminder_txt.text.toString()
                    )
                    addTask(nTask)
                    replaceFragment()
                }
            }

        }

        view.cancel_task.setOnClickListener {
            replaceFragment()
        }

        view.back_task.setOnClickListener {
            replaceFragment()
        }

        view.task_reminder.setOnClickListener {
            setTaskReminder(view)
        }
    }

    private fun replaceFragment(){
        fragmentManager?.popBackStack()
    }

    private fun addTask(task: TaskModel){
        val id = UUID.randomUUID()
        task.id = id.mostSignificantBits

        if (reminderTime != 0L)
            setReminderNotify(reminderTime, task)

        taskViewModel.insertTask(task)
    }

    fun updateTask(task: TaskModel){

        if (reminderTime != 0L)
            setReminderNotify(reminderTime,task)

        taskViewModel.updateTask(task)
    }

    private fun setTaskReminder(tView: View){
        val calendar: Calendar = Calendar.getInstance()


        val dialogBuilder  = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.reminder_picker, null)
        dialogBuilder .setView(dialogView)

        val minHour = calendar.get(Calendar.HOUR_OF_DAY)
        val minMinute = calendar.get(Calendar.MINUTE)

        dialogView.reminder_hour.wrapSelectorWheel = true
        dialogView.reminder_hour.minValue = minHour
        dialogView.reminder_hour.setFormatter {
            if (it > minHour) {
                dialogView.reminder_minute.minValue = 0
            } else {
                dialogView.reminder_minute.minValue = minMinute + 1
            }

            return@setFormatter when(it){
                24 -> "00"
                in 1..9 -> "0${it}"
                else -> it.toString()
            }
        }

        dialogView.reminder_minute.wrapSelectorWheel = true
        dialogView.reminder_minute.setFormatter {
                return@setFormatter when(it){
                    60 -> "00"
                    in 1..9 -> "0${it}"
                    else -> it.toString()
                }
        }

        dialogBuilder.setNegativeButton("Cancel", null)
        dialogBuilder.setPositiveButton("Ok") { _, _ ->
            calendar.apply {
                set(Calendar.HOUR_OF_DAY, dialogView.reminder_hour.value)
                set(Calendar.MINUTE, dialogView.reminder_minute.value)
                set(Calendar.SECOND, 0)
            }
            val time = LocalTime.of(dialogView.reminder_hour.value, dialogView.reminder_minute.value)
                .format(DateTimeFormatter.ofPattern("hh:mm a"))
            tView.reminder_txt.text = time
            reminderTime = calendar.timeInMillis
        }


        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.color.colorSecondary)
        alertDialog.show()
    }

    private fun setReminderNotify(timeMs: Long, task: TaskModel){

        val intent = Intent(context, ReminderBroadCast::class.java).apply {
            putExtra("task",task.title)
            putExtra("details",task.description)
            putExtra("notification", task.id)
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }


        val pendingIntent = PendingIntent.getBroadcast(context, task.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context?.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeMs,
                pendingIntent
            )

    }

    companion object {
        fun newInstance(task: TaskModel? = null,dayId: Long?): TaskFragment =
            TaskFragment().apply {
                this.task = task
                this.dayId = dayId
            }
    }

    fun <T : Any> argumentNullable(): ReadWriteProperty<Fragment, T?> =
        FragmentNullableArgumentDelegate()

}

