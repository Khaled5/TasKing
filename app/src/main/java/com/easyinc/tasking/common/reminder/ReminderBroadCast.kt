package com.easyinc.tasking.common.reminder

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.easyinc.tasking.presentation.MainActivity
import com.easyinc.tasking.R


class ReminderBroadCast : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {

        val task: String? = intent?.getStringExtra("task")
        val details: String? = intent?.getStringExtra("details")
        val notificationId = intent?.getLongExtra("notification",System.currentTimeMillis())

        val moveToActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, moveToActivityIntent, 0)

        val builder = NotificationCompat.Builder(context!!, "CHANNEL_1_ID")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(task)
            .setContentText(details)
            .setColor(Color.parseColor("#03DAC5"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setFullScreenIntent(pendingIntent, true)


        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(
                context,
                notification
            )
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId!!.toInt(), builder.build())
        }

    }

    /*fun test(ctx: Context){
        val intent = Intent(ctx, ReminderBroadCast1::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = ctx.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

        val calendar   = Calendar.getInstance()
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 13)
        calendar.set(Calendar.HOUR, 7)
        calendar.set(Calendar.AM_PM, Calendar.AM)
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 9)
        calendar.set(Calendar.YEAR, 2015)

        val f: Long

        if (Build.VERSION.SDK_INT < 19) {
            am.set(AlarmManager.RTC_WAKEUP, timeMs, pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, timeMs, pendingIntent);
        }
    }*/
}