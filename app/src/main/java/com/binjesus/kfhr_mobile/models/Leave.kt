package com.binjesus.kfhr_mobile.models

data class Leave(
    val id: Int,
    val employeeId: Int,
    val leaveTypes: Int,
    val startDate: String,
    val endDate: String,
    val status: String?,
    val notes: String
)

