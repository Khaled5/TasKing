package com.easyinc.tasking.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easyinc.tasking.cache.database.dao.DayDao
import com.easyinc.tasking.cache.database.dao.TaskDao
import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.cache.entity.Task

@Database(entities = [Day::class,Task::class], version = 1, exportSchema = false)
public abstract class TasksRoomDatabase : RoomDatabase() {

    abstract fun dayDao(): DayDao
    abstract fun taskDao(): TaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TasksRoomDatabase? = null

        fun getDatabase(context: Context): TasksRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksRoomDatabase::class.java,
                    "task_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}