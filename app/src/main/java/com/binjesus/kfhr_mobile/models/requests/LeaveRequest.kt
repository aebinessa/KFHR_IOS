package com.binjesus.kfhr_mobile.models.requests

data class LeaveRequest(
    val employeeId: Int,
    val leaveType: String,
    val startDate: String,
    val endDate: String,
    val notes: String
)