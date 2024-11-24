package com.example.cellcom_exam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Query


interface TMDbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse
}

data class MovieResponse(val results: List<Movie>)

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    var year: Int
) : Parcelable
