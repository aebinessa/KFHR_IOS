package com.binjesus.kfhr_mobile.utils

class Endpoint {
    companion object {
        const val baseUrl = "https://kfhrbackend20240528002909.azurewebsites.net/"
        const val sigInEndpoint = "api/Authentication/Login"
        const val getEmployeesEndpoint = "api/User/Employees"
        const val authorization = "Authorization"
    }
}