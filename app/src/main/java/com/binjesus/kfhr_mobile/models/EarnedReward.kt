package com.binjesus.kfhr_mobile.models

import java.util.Date

data class EarnedReward(
    val id: Int,
    val rewardId: Int,
    val employeeId: Int,
    val redemptionDate: Date
)
