package com.binjesus.kfhr_mobile.network

import com.binjesus.kfhr_mobile.models.Employee
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val employee: Employee)
interface KFHRApiService {
    @POST("Authentication/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}