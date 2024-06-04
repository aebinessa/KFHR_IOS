package com.binjesus.kfhr_mobile.models.requests

import java.util.Date

data class CheckOutRequest(
    val id: Int,
    val employeeId: Int,
    val checkOutDateTime: Date,
)