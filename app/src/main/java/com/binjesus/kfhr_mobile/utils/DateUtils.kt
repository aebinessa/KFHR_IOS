package com.binjesus.kfhr_mobile.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


fun convertDateToTimeString(stringDate: String): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = convertStringToDate(stringDate)
    return timeFormat.format(date)

}

fun convertDateToDayOfWeek(stringDate: String): String {
    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val date = convertStringToDate(stringDate)
    return dayOfWeekFormat.format(date)
}

fun convertDateToBasicDateStringFormat(stringDate: String): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = convertStringToDate(stringDate)
    return dateFormat.format(date)
}

fun convertStringToDate(stringDate: String): Date {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    // Parse the date string to LocalDateTime
    val localDateTime = LocalDateTime.parse(
        stringDate.substring(0, 16), formatter
    ).plusHours(3.toLong())

    // Convert LocalDateTime to Date
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}

fun convertDateToString(date: Date): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    // Convert LocalDateTime to Date
    return formatter.format(date)
}