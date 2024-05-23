package com.binjesus.kfhr_mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.network.KFHRApiService
import com.binjesus.kfhr_mobile.network.RetrofitHelper
import kotlinx.coroutines.launch

class KFHRViewModel : ViewModel() {
    private val apiService = RetrofitHelper.getInstance().create(KFHRApiService::class.java)
    var token: TokenResponse? by mutableStateOf(null)


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

}