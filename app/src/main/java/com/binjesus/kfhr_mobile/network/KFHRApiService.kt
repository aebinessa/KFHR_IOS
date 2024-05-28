package com.binjesus.kfhr_mobile.network

import com.binjesus.kfhr_mobile.models.Employee
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val employee: Employee)

data class LeaveRequest(
    val employeeId: Int,
    val leaveType: String,
    val startDate: String,
    val endDate: String,
    val notes: String
)
interface KFHRApiService {
    @POST("Authentication/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
interface LeaveService {
    @POST("api/admin/leavesResponse")
    suspend fun applyForLeave(@Body leaveRequest: LeaveRequest): Response<Void>
}

// Create Retrofit instance
val retrofit = Retrofit.Builder()
    .baseUrl("https://yourapi.com/") // Replace with your API base URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val leaveService = retrofit.create(LeaveService::class.java)