package com.example.cellcom_exam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object ApiService {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "fe07d19ba4ca2fc393f27f30998b5f94"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
