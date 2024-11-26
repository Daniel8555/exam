package com.example.cellcom_exam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.cellcom_exam.databinding.ActivityMovieDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieDetailsActivity : AppCompatActivity() {
private val favoritesManager by lazy { FavoritesManager.initialize(this) }
private lateinit var favoriteButton: ImageButton
private lateinit var topLottieAnimation : LottieAnimationView
private lateinit var movie: Movie
private lateinit var keyResponse : String
    private val movieApiService by lazy {
    ApiService.createService(MovieApiService::class.java)
}

override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
        val binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    GlobalScope.launch(Dispatchers.Main) {
        try {
            keyResponse =  movieApiService.getMovieVideos(movie.id,ApiService.API_KEY).results.filter { it.type == "Trailer" && it.site == "YouTube" && it.official  }[0].key
            Log.e("movies","url")
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error fetching videos", e)
        }
    }



    movie = intent.getParcelableExtra<Movie>("movie_key")
        ?: throw IllegalStateException("Movie data not found")


    val movieTitle: TextView = binding.movieTitle
    val movieDescription: TextView = binding.movieOverview
    val moviePoster : ImageView = binding.moviePoster
    favoriteButton = binding.favBtn
    topLottieAnimation = binding.topLottieAnimation


    movieTitle.text = movie.title
    movieDescription.text = movie.overview

       Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .into(moviePoster)

    binding.playIcon.setOnClickListener(View.OnClickListener {
        try {
            if(!keyResponse.isEmpty())
            {
                val trailerUrl = "https://www.youtube.com/embed/$keyResponse"
                val intent = Intent(this, TrailerActivity::class.java)
                intent.putExtra("TRAILER_URL", trailerUrl)
                startActivity(intent)
            }
        }
        catch (e:Exception)
        {
            Log.e("keyResponse","key response is empty")
            Toast.makeText(this, "The trailer not available", Toast.LENGTH_SHORT).show()
        }

    })


    updateFavoriteIcon()


    favoriteButton.setOnClickListener {
        if (FavoritesManager.getFavoriteMovies().any { it.id == movie.id }) {
            FavoritesManager.removeFavorite(movie)

        } else {
            FavoritesManager.addFavorite(movie)
                topLottieAnimation.isVisible = true
                Handler(Looper.getMainLooper()).postDelayed({
                    topLottieAnimation.isVisible = false
                }, 750)



        }
        updateFavoriteIcon()
    }
}



private fun updateFavoriteIcon() {
    val isFavorite = FavoritesManager.getFavoriteMovies().any { it.id == movie.id }
    favoriteButton.setImageResource(
        if (isFavorite) R.drawable.ic_red_favorite_24 else R.drawable.ic_baseline_favorite_border_24
    )

    }
}
