package com.binjesus.kfhr_mobile.models.responses

import java.util.Date

data class LeaveStatusResponse(
    val id: Int,
    val employeeId: Int,
    val status: String,
)