package com.binjesus.kfhr_mobile.models

import java.util.Date

data class Leave(
    val id: Int,
    val employeeId: Int,
    val leaveType: String,
    val startDate: Date,
    val endDate: Date,
    val status: String,
    val notes: String
)
