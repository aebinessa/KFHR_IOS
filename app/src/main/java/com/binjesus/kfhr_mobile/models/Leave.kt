package com.binjesus.kfhr_mobile.models

data class Leave(
    val id: Int,
    val employeeId: Int,
    val leaveType: String,
    val startDate: String,
    val endDate: String,
    val status: String?,
    val notes: String
)

data class LeaveRequest(
    val leaveTypes: Int,
    val startDate: String,
    val endDate: String,
    val notes: String
)
enum class LeaveType(val value: Int) {
    Annual(0),
    Sick(1),
    Emergency(2);

    fun toInt(): Int {
        return value
    }

    companion object {
        fun fromInt(value: Int): LeaveType? {
            return values().find { it.value == value }
        }
    }
}
