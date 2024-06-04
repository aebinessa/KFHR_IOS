package com.binjesus.kfhr_mobile.utils

class Endpoint {
    companion object {
        const val baseUrl = "https://kfhrbackend20240528002909.azurewebsites.net/"
        const val sigInEndpoint = "api/Authentication/Login"
        const val getEmployeesEndpoint = "api/User/Employees"
        const val getRecommendedCertificatesEndpoint = "api/User/GetCertificates"
        const val submitCertificateEndpoint = "api/User/AddCertificate"
        const val submitLeaveEndpoint = "api/User/Leave"
        const val getLeaveStatusEndpoint = "api/User/Leave"
        const val checkInEndPoint = "api/User/CheckInEmployee"
        const val checkOutEndPoint = "api/User/CheckOutEmployee"
        const val getAttendanceRecordEndpoint = ""
        const val getLateMinutesLeftEndpoint = ""

    }
}