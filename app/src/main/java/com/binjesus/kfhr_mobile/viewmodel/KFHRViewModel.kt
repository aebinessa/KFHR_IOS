package com.binjesus.kfhr_mobile.viewmodel

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
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.network.KFHRApiService
import com.binjesus.kfhr_mobile.network.LeaveRequest
import com.binjesus.kfhr_mobile.network.LeaveService
import com.binjesus.kfhr_mobile.network.RetrofitHelper
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Calendar
import java.util.Date

class KFHRViewModel : ViewModel() {
    private val apiService = RetrofitHelper.getInstance().create(KFHRApiService::class.java)
    var token: TokenResponse? by mutableStateOf(null)
    var selectedDirectoryEmployee: Employee? by mutableStateOf(null)
    var certificates: List<Certificate> by mutableStateOf(emptyList())
    var recommendedCertificates: List<RecommendedCertificate> by mutableStateOf(emptyList())
    var notifications: List<NotificationItem> by mutableStateOf(emptyList())
    var isLeaveRequestSuccessful: Boolean by mutableStateOf(false)

    // TODO convert to states employee, attendance, lateMinutesLeft
    val employee = Employee(
        id = 1,
        name = "Abdullah Bin Essa",
        role = "Forssah Tech Trainee",
        email = "abdullah@example.com",
        phone = "123456789",
        dob = Date(),
        gender = "Male",
        profilePicURL = "https://example.com/profile.jpg",
        nfcIdNumber = 123,
        positionId = 1,
        departmentId = 1,
        pointsEarned = 100
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
    val employees = listOf(
        Employee(
            1,
            "Feras Alshadad",
            "Role",
            "email@example.com",
            "123456789",
            Date(),
            "Male",
            "https://example.com/profile1.jpg",
            123,
            1,
            1,
            100
        ),
        Employee(
            2,
            "Abdullah Bin Essa",
            "Role",
            "email@example.com",
            "123456789",
            Date(),
            "Male",
            "https://example.com/profile2.jpg",
            124,
            2,
            2,
            100
        ),
        Employee(
            3,
            "Othman Alkous",
            "Role",
            "email@example.com",
            "123456789",
            Date(),
            "Male",
            "https://example.com/profile3.jpg",
            125,
            3,
            3,
            100
        ),
        // Add more employees as needed
    )

    /* fun signup(username: String, password: String, image: String = "") {
         viewModelScope.launch {
             try {
                 val response = apiService.signup(User(username, password, image, null))
                 token = response.body()
             } catch (e: Exception) {
                 println("Error $e")
             }

         }
     }*/


    // TODO remove init
    init {
        fetchNotifications()
        fetchMyCertificates()
        fetchRecommendedCertificates()
    }

    fun signIn(email: String, password: String) {
        TODO("Not yet implemented")
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
            val apiService = RetrofitHelper.getInstance().create(LeaveService::class.java)
            try {
                val leaveRequest = LeaveRequest(employeeId, leaveType, startDate, endDate, notes)
                val response = apiService.applyForLeave(leaveRequest)
                isLeaveRequestSuccessful = response.isSuccessful
            } catch (e: retrofit2.HttpException) {

            } catch (e: IOException) {

            }
        }
    }

}