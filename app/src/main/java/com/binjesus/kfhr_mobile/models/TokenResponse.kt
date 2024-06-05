package com.binjesus.kfhr_mobile.models

data class TokenResponse(
    val token: String?,
    val employeeId: Int,
    val employeeName: String?,
    val employeePic: String?,
    val employeeNfc: Int?,
    val employeeDepartmentId: Int?
) {
    fun getBearerToken(): String {
        return "Bearer $token"
    }
}