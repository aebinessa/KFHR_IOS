package com.binjesus.kfhr_mobile.network

import com.binjesus.kfhr_mobile.utils.Endpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoint.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}