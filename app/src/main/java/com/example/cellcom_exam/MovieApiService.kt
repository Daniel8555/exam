package com.example.cellcom_exam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Query


import retrofit2.http.Path


interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): VideoResponse
}


@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?
) : Parcelable

data class MovieResponse(
    val results: List<Movie>
)


data class VideoResponse(
    val results: List<Video>
)

data class Video(
    val key: String,
    val name: String,
    val site: String,
    val type: String
)
