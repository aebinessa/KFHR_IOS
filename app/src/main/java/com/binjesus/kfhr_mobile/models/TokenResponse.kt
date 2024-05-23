package com.binjesus.kfhr_mobile.models

data class TokenResponse(val token: String?) {
    fun getBearerToken(): String {
        return "Bearer $token"
    }
}