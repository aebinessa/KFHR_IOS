package com.binjesus.kfhr_mobile.network

import com.binjesus.kfhr_mobile.models.Attendance
import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.models.LateMinutesLeft
import com.binjesus.kfhr_mobile.models.Leave
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.models.requests.CheckInRequest
import com.binjesus.kfhr_mobile.models.requests.CheckOutRequest
import com.binjesus.kfhr_mobile.models.requests.LoginRequest
import com.binjesus.kfhr_mobile.utils.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface KFHRApiService {
    @POST(Endpoint.sigInEndpoint)
    suspend fun signIn(@Body request: LoginRequest): Response<TokenResponse>
    @GET(Endpoint.getEmployeesEndpoint)
    suspend fun getEmployees(@Header("Authorization") token: String): List<Employee>
    @GET(Endpoint.getRecommendedCertificatesEndpoint)
    suspend fun getRecommendedCertificates(@Header("Authorization") token: String): List<RecommendedCertificate>
    @POST(Endpoint.submitCertificateEndpoint)
    suspend fun submitCertificate(@Header("Authorization") token: String, @Body certificate: Certificate): Response<Void>
    @POST(Endpoint.checkInEndPoint)
    suspend fun checkIn(@Header("Authorization") token: String) : Response<Void>
    @POST(Endpoint.checkOutEndPoint)
    suspend fun checkOut(@Header("Authorization") token: String) : Response<Void>
    @GET(Endpoint.getLeaveEndpoint)
    suspend fun getLeave(@Header("Authorization") token: String): List<Leave>
    @GET(Endpoint.getLateMinutesLeftEndpoint)
    suspend fun getLateMinutesLeft(@Header("Authorization") token: String) : Response<LateMinutesLeft>
    @GET(Endpoint.getAttendanceRecordEndpoint)
    suspend fun getAttendanceRecord(@Header("Authorization") token: String) : List<Attendance>
    @GET(Endpoint.getTodayAttendanceEndpoint)
    suspend fun getTodayAttendance(@Header("Authorization") token: String) : Response<Attendance>
    @GET(Endpoint.getSubmittedCertificatesEndpoint)
    suspend fun getMyCertificates(@Header("Authorization") token: String) : List<Certificate>

    @POST(Endpoint.submitLeaveEndpoint)
    suspend fun applyForLeave(@Header("Authorization") token: String, @Body newLeave: com.binjesus.kfhr_mobile.models.LeaveRequest): Response<Void>
}