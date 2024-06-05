package com.binjesus.kfhr_mobile.models

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class Certificate(
    val id: Int,
    val employeeId: Int,
    val certificateName: String,
    val issueDate: String?,
    val expirationDate: String?,
    val verificationURL: String
)
