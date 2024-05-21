package com.binjesus.kfhr_mobile.models

import java.util.Date

data class Certificate(
    val id: Int,
    val employeeId: Int,
    val certificateName: String,
    val issueDate: Date,
    val expirationDate: Date,
    val verificationURL: String
)
