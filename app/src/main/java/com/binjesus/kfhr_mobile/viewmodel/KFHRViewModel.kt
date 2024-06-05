package com.binjesus.kfhr_mobile.viewmodel

import android.icu.text.SimpleDateFormat
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
import java.util.Locale

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
    var employee: TokenResponse? by mutableStateOf(null)
    var attendances by mutableStateOf<List<Attendance>>(emptyList())
    val lateMinutesLeft : LateMinutesLeft? by mutableStateOf(null)




//
fun signIn(email: String, password: String) {
    viewModelScope.launch {
        try {
            val loginRequest = LoginRequest(email, password)
            val response: Response<TokenResponse> = apiService.signIn(loginRequest)
            Log.e("HELLO", response.message())
            Log.e("HELLO", response.isSuccessful.toString())
            Log.e("HELLO", response.body()?.token!!)
            if (response.isSuccessful) {
                employee = response.body()
                token = employee?.token // Extract the token string
            } else {
                errorMessage.value = "Login failed: ${response.message()}"
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
    fun getRecommendedCertificates() {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.getRecommendedCertificates("Bearer $authToken")
                    if (response.isNotEmpty()) {
                        recommendedCertificates = response
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
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.submitCertificate("Bearer $authToken", newCertificate)
                    if (response.isSuccessful) {
                        certificates = certificates + newCertificate
                        errorMessage.value = ""
                        Log.d("KFHRViewModel", "Certificate submitted successfully")
                    } else {
                        errorMessage.value = "Submission failed: ${response.message()}"
                        Log.e("KFHRViewModel", "Submission failed: ${response.message()}")
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
    fun getAttendances() {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.getAttendanceRecord("Bearer $authToken")
                    if (response.isNotEmpty()) {
                        attendances = response
                        errorMessage.value = ""
                        Log.d("KFHRViewModel", "Attendances fetched successfully")
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

    val attendance = Attendance(
        id = 1,
        employeeId = 1,
        checkInDateTime = Date(),
        checkOutDateTime = Date(Date().time + 3600000) // 1 hour later
    )
    // TODO remove init
    init {
        fetchNotifications()

    }

}













