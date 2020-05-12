package com.easyinc.tasking.data.model

data class DayModel(
    val date: String,
    var progressPercent: String = "0%",
    val id: Long = 0
)