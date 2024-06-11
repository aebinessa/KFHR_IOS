package com.binjesus.kfhr_mobile.models

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class Attendance(
    val id: Int,
    val employeeId: Int,
    val checkInTime: String?,
    val checkOutTime: String?
) {

    fun checkInDateTimeObject(): Date {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        // Parse the date string to LocalDateTime
        val localDateTime = LocalDateTime.parse(checkInTime?.substring(0, 16), formatter).plusHours(3.toLong())

        // Convert LocalDateTime to Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun checkOutDateTimeObject(): Date {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        // Parse the date string to LocalDateTime
        val localDateTime = LocalDateTime.parse(checkOutTime?.substring(0, 16), formatter).plusHours(3.toLong())


        // Convert LocalDateTime to Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}
