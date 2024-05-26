package com.binjesus.kfhr_mobile.models

import java.util.Date

data class Employee(
    val id: Int,
    val name: String,
    val role: String,
    val email: String,
    val phone: String,
    val dob: Date,
    val gender: String,
    val profilePicURL: String,
    val nfcIdNumber: Int,
    val positionId: Int,
    val departmentId: Int,
    val pointsEarned: Int
)
