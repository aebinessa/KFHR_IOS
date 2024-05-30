package com.binjesus.kfhr_mobile.network

import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.models.requests.LeaveRequest
import com.binjesus.kfhr_mobile.models.requests.LoginRequest
import com.binjesus.kfhr_mobile.models.responses.LoginResponse
import com.binjesus.kfhr_mobile.utils.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KFHRApiService {
    @POST(Endpoint.sigInEndpoint)
    suspend fun signIn(@Body request: LoginRequest): Response<TokenResponse>

    @POST("api/admin/leavesResponse")
    suspend fun applyForLeave(@Body leaveRequest: LeaveRequest): Response<Void>
}