package com.binjesus.kfhr_mobile.models

import java.util.Date

data class Attendance(
    val id: Int,
    val employeeId: Int,
    val checkInDateTime: Date,
    val checkOutDateTime: Date
)
