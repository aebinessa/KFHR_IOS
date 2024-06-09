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
import com.binjesus.kfhr_mobile.models.Leave
import com.binjesus.kfhr_mobile.models.LeaveRequest
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.models.requests.LoginRequest
import com.binjesus.kfhr_mobile.network.KFHRApiService
import com.binjesus.kfhr_mobile.network.RetrofitHelper
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class KFHRViewModel : ViewModel() {
    var attendance: Attendance? by mutableStateOf(null)
    private val apiService = RetrofitHelper.getInstance().create(KFHRApiService::class.java)
    var token: String? by mutableStateOf(null)
    var showValidationError: Boolean by mutableStateOf(false)
    var selectedDirectoryEmployee: Employee? by mutableStateOf(null)
    var recommendedCertificates: List<RecommendedCertificate> by mutableStateOf(emptyList())
    var notifications: List<NotificationItem> by mutableStateOf(emptyList())
    var isLeaveRequestSuccessful: Boolean by mutableStateOf(false)
    val employees = mutableStateOf<List<Employee>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")
    var employee: TokenResponse? by mutableStateOf(null)
    var attendances by mutableStateOf<List<Attendance>>(emptyList())
    var lateMinutesLeft: LateMinutesLeft? by mutableStateOf(null)
    var MyCertificates by mutableStateOf<List<Certificate>>(emptyList())
    var leaves by mutableStateOf<List<Leave>>(emptyList())

    var isSubmitedCertificateSuccessful: Boolean by mutableStateOf(false)

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
                        for (employee in employees.value) {
                            Log.e("KFHRViewModel", employee.toString())
                        }

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

    fun getMyCertificates() {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.getMyCertificates("Bearer $authToken")
                    if (response.isNotEmpty()) {
                        MyCertificates = response
                        errorMessage.value = ""
                        Log.d("KFHRViewModel", "Certificates fetched successfully")

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

    fun fetchLateMinutesLeft() {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.getLateMinutesLeft("Bearer $authToken")
                    lateMinutesLeft = response.body()
                    errorMessage.value = ""
                    Log.d("KFHRViewModel", "Late minutes fetched successfully")
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

    fun fetchLeave() {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response = apiService.getLeave("Bearer $authToken")
                    leaves = response
                    errorMessage.value = ""
                    Log.d("KFHRViewModel", "Leave  fetched successfully")
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
                        isSubmitedCertificateSuccessful = true
                        errorMessage.value = ""
                        Log.d("KFHRViewModel", "Certificate submitted successfully")
                    } else {
                        isSubmitedCertificateSuccessful = false
                        errorMessage.value = "Submission failed: ${response.message()}"
                        Log.e("KFHRViewModel", "Submission failed: ${response.message()}")
                    }
                } catch (e: Exception) {
                    isSubmitedCertificateSuccessful = false
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

    fun applyForLeave(newLeave: LeaveRequest) {
        token?.let { authToken ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    val response: Response<Void> =
                        apiService.applyForLeave("Bearer $authToken", newLeave)
                    Log.d("ApplyForLeave", "Received response: ${response.message()}")
                    Log.d("ApplyForLeave", "Received response: ${response.code()}")

                    if (response.isSuccessful) {
                        isLeaveRequestSuccessful = true
                    } else {
                        Log.d("ApplyForLeave", "Leave request failed")

                        isLeaveRequestSuccessful = false
                        errorMessage.value = "Failed to apply for leave. Please try again."
                    }
                } catch (e: HttpException) {
                    errorMessage.value = "HTTP error: ${e.message}"
                } catch (e: IOException) {
                    errorMessage.value = "Network error: ${e.message}"
                } finally {
                    isLoading.value = false
                }
            }
        } ?: run {
            errorMessage.value = "Token is null"
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

    fun getTodayAttendance() {
        token?.let { authToken ->
            viewModelScope.launch {
                attendance = apiService.getTodayAttendance("Bearer $authToken").body()
            }
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


    // TODO remove init
    init {
        fetchNotifications()

    }

}













