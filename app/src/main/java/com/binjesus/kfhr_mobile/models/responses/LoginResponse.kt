package com.binjesus.kfhr_mobile.models.responses

import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.models.TokenResponse

data class LoginResponse(val employee: Employee, val tokenResponse: TokenResponse)
