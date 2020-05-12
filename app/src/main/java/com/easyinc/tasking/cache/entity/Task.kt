package com.easyinc.tasking.cache.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    val title: String?,
    val description: String?,
    @ColumnInfo(name = "day_id")
    val dayId: Long?,
    val reminder: String? = "",
    @ColumnInfo(name = "is_done")
    val isDone: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0
) : Parcelable {



    constructor(parcel: Parcel) : this(parcel.readString(),parcel.readString(),parcel.readLong(),parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}