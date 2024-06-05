package com.binjesus.kfhr_mobile.models

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class Attendance(
    val id: Int,
    val employeeId: Int,
    val checkInDateTime: String?,
    val checkOutDateTime: String?
) {

    fun checkInDateTimeObject(): Date {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        // Parse the date string to LocalDateTime
        val localDateTime = LocalDateTime.parse(checkInDateTime, formatter)

        // Convert LocalDateTime to Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun checkOutDateTimeObject(): Date {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        // Parse the date string to LocalDateTime
        val localDateTime = LocalDateTime.parse(checkOutDateTime, formatter)

        // Convert LocalDateTime to Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}
