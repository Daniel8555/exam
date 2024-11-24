package com.example.cellcom_exam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "fe07d19ba4ca2fc393f27f30998b5f94"

    val tmdbApi: TMDbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDbApi::class.java)
    }

    suspend fun getPopularMovies(): List<Movie> {
        return tmdbApi.getPopularMovies(API_KEY).results
    }
}
