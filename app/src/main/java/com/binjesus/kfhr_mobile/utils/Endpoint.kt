package com.binjesus.kfhr_mobile.utils

class Endpoint {
    companion object {
        const val baseUrl = "https://kfhrbackend20240528002909.azurewebsites.net/"
        const val sigInEndpoint = "api/Authentication/Login"
        const val getEmployeesEndpoint = "api/User/Employees"
        const val getRecommendedCertificatesEndpoint = "api/User/GetRecommendedCertificates"
        const val submitCertificateEndpoint = "api/User/AddCertificate"
        const val submitLeaveEndpoint = "api/User/Leave"
        const val getLeaveEndpoint = "api/User/Leave"
        const val checkInEndPoint = "api/User/CheckInEmployee"
        const val checkOutEndPoint = "api/User/CheckOutEmployee"
        const val getAttendanceRecordEndpoint = "api/User/GetAttendance"
        const val getLateMinutesLeftEndpoint = "api/User/GetLateMinutes"
        const val getTodayAttendanceEndpoint = "api/User/GetTodayAttendance"
        const val getSubmittedCertificatesEndpoint = "api/User/GetCertificates"

    }
}