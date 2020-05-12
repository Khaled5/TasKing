package com.easyinc.tasking.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_table")
data class Day(
    val date: String,
    @PrimaryKey
    val id: Long = 0
)