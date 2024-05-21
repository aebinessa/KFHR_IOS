package com.binjesus.kfhr_mobile.models

import java.util.Date

data class Employee(
    val id: Int,
    val password: String,
    val name: String,
    val role: String,
    val email: String,
    val phone: String,
    val dob: Date,
    val gender: String,
    val profilePicURL: String,
    val nfcId: Int,
    val positionId: Int,
    val departmentId: Int,
    val pointsEarned: Int
)
