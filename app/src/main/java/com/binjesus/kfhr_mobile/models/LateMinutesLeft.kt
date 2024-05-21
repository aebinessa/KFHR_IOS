package com.binjesus.kfhr_mobile.models

import java.util.Date

data class LateMinutesLeft(
    val id: Int,
    val employeeId: Int,
    val time: Int,
    val month: Date
)
