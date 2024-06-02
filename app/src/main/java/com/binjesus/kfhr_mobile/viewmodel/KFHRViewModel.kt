package com.binjesus.kfhr_mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binjesus.kfhr_mobile.composables.NotificationItem
import com.binjesus.kfhr_mobile.models.Attendance
import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.models.LateMinutesLeft
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.models.requests.LeaveRequest
import com.binjesus.kfhr_mobile.models.requests.LoginRequest
import com.binjesus.kfhr_mobile.network.KFHRApiService
import com.binjesus.kfhr_mobile.network.RetrofitHelper
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class KFHRViewModel : ViewModel() {
    private val apiService = RetrofitHelper.getInstance().create(KFHRApiService::class.java)
    var token: String? by mutableStateOf(null)
    var showValidationError: Boolean by mutableStateOf(false)
    var selectedDirectoryEmployee: Employee? by mutableStateOf(null)
    var certificates: List<Certificate> by mutableStateOf(emptyList())
    var recommendedCertificates: List<RecommendedCertificate> by mutableStateOf(emptyList())
    var notifications: List<NotificationItem> by mutableStateOf(emptyList())
    var isLeaveRequestSuccessful: Boolean by mutableStateOf(false)
    val employees = mutableStateOf<List<Employee>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")



    // TODO convert to states employee, attendance, lateMinutesLeft


    // TODO remove init
    init {
        fetchNotifications()
        fetchMyCertificates()
        fetchRecommendedCertificates()
    }

//
fun signIn(email: String, password: String) {
    viewModelScope.launch {
        try {
            val loginRequest = LoginRequest(email, password)
            val response: Response<TokenResponse> = apiService.signIn(loginRequest)

            if (response.isSuccessful) {
                token = response.body()?.token // Extract the token string
                Log.d("KFHRViewModel", "Token received: $token")
                Log.d("KFHRViewModel", "SignIn Status Code ${response.code()}")
            } else {
                errorMessage.value = "Login failed: ${response.message()}"
                Log.e("KFHRViewModel", "Login failed: ${response.message()}")
            }

        } catch (e: Exception) {
            errorMessage.value = "Login error: ${e.message}"
            Log.e("KFHRViewModel", "Login error: ${e.message}")
        }
    }
}

     fun getEmployees() {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.getEmployees("Bearer $authToken")
                    if (response.isNotEmpty()) {
                        employees.value = response
                        errorMessage.value = ""
                        Log.d("KFHRViewModel", "Employees fetched successfully")

                    }
                } catch (e: Exception) {
                    errorMessage.value = "Error: ${e.message}"
                    Log.e("KFHRViewModel", "Error: $e")
                } finally {
                    isLoading.value = false
                }
            }
        } ?: run {
            errorMessage.value = "Error: Token is null"
            Log.e("KFHRViewModel", "Error: Token is null")
        }
    }


    fun submitCertificate(newCertificate: Certificate) {
        TODO("Not yet implemented")
    }

    fun fetchRecommendedCertificates() {
        recommendedCertificates = listOf()
    }

    fun fetchMyCertificates() {
        certificates = listOf(
            Certificate(1, 1, "Certificate 1", Date(), Date(), "https://example.com"),
            Certificate(2, 1, "Certificate 2", Date(), Date(), "https://example.com"),
            Certificate(3, 1, "Certificate 3", Date(), Date(), "https://example.com"),
            Certificate(4, 1, "Certificate 4", Date(), Date(), "https://example.com"),
            Certificate(5, 1, "Certificate 5", Date(), Date(), "https://example.com")
        )
    }

    fun fetchNotifications() {
        notifications = listOf(
            NotificationItem("Leave Request", "Just Now"),
            NotificationItem("New Message", "5 minutes ago"),
            NotificationItem("Meeting Reminder", "10 minutes ago"),
            NotificationItem("Meeting Reminder", "10 minutes ago"),
            NotificationItem("Meeting Reminder", "10 minutes ago"),
            NotificationItem("Meeting Reminder", "10 minutes ago"),
            NotificationItem("Meeting Reminder", "10 minutes ago"),
            NotificationItem("Meeting Reminder", "10 minutes ago")
            // Add more notifications as needed
        )
    }

    fun applyForLeave(
        employeeId: Int,
        leaveType: String,
        startDate: String,
        endDate: String,
        notes: String
    ) {
        viewModelScope.launch {
            try {
                val leaveRequest = LeaveRequest(employeeId, leaveType, startDate, endDate, notes)
                val response = apiService.applyForLeave(leaveRequest)
                isLeaveRequestSuccessful = response.isSuccessful
            } catch (e: retrofit2.HttpException) {

            } catch (e: IOException) {

            }
        }
    }

    val employee = Employee(
        id = 1,
        name = "Abdullah Bin Essa",
        password = "qwerty",
        email = "abdullah@example.com",
        dob = "",
        gender = 1,
        profilePicURL = "https://example.com/profile.jpg",
        nfcIdNumber = 123,
        positionId = 2,
        departmentId = 1,
        pointsEarned = 100,
        isAdmin = false
    )
    val attendance = Attendance(
        id = 1,
        employeeId = 1,
        checkInDateTime = Date(),
        checkOutDateTime = Date(Date().time + 3600000) // 1 hour later
    )
    val lateMinutesLeft = LateMinutesLeft(
        id = 1,
        employeeId = 1,
        time = 27,
        month = Date()
    )

    val attendances = listOf(
        Attendance(
            id = 1,
            employeeId = 101,
            checkInDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 1, 8, 30)
            }.time,
            checkOutDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 1, 17, 0)
            }.time
        ),
        Attendance(
            id = 2,
            employeeId = 101,
            checkInDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 2, 8, 45)
            }.time,
            checkOutDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 2, 17, 15)
            }.time
        ),
        Attendance(
            id = 3,
            employeeId = 101,
            checkInDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 3, 9, 0)
            }.time,
            checkOutDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 3, 18, 0)
            }.time
        )
    )
}













