package com.easyinc.tasking.data.model

import android.os.Parcel
import android.os.Parcelable

data class TaskModel(
    val title: String?,
    val description: String?,
    val dayId: Long?,
    val reminder: String? = "",
    val isDone: Boolean = false,
    var id: Long = 0
) : Parcelable {



    constructor(parcel: Parcel) : this(parcel.readString(),parcel.readString(),parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }
}