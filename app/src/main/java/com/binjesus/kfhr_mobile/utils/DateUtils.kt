package com.binjesus.kfhr_mobile.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun convertStringToDate(stringDate: String): Date {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    // Parse the date string to LocalDateTime
    val localDateTime = LocalDateTime.parse(stringDate, formatter)

    // Convert LocalDateTime to Date
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}

fun convertDateToString(date: Date): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    // Convert LocalDateTime to Date
    return formatter.format(date)
}