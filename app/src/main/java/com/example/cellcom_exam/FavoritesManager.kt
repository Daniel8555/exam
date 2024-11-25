package com.example.cellcom_exam

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    fun addFavorite(movie: Movie) {
        val editor = sharedPreferences.edit()
        val movies = getFavoriteMovies().toMutableList()
        movies.add(movie)
        editor.putString("movies", Gson().toJson(movies))
        editor.apply()
    }

    fun removeFavorite(movie: Movie) {
        val editor = sharedPreferences.edit()
        val movies = getFavoriteMovies().toMutableList()
        movies.removeIf { it.id == movie.id }
        editor.putString("movies", Gson().toJson(movies))
        editor.apply()
    }

    fun getFavoriteMovies(): List<Movie> {
        val json = sharedPreferences.getString("movies", null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(json, type)
    }
}
