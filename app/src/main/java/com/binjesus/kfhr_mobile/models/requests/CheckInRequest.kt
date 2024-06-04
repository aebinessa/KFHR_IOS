package com.binjesus.kfhr_mobile.models.requests

import java.util.Date

data class CheckInRequest(
    val id: Int,
    val employeeId: Int,
    val checkInDateTime: Date,
)
