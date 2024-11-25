package com.example.cellcom_exam

import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.cellcom_exam.databinding.ActivityMovieDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieDetailsActivity : AppCompatActivity() {
private lateinit var favoritesManager: FavoritesManager
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


    // Initialize FavoritesManager
    favoritesManager = FavoritesManager(this)

    // Get movie data from intent
    movie = intent.getParcelableExtra<Movie>("movie_key")
        ?: throw IllegalStateException("Movie data not found")

    // Bind UI elements
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
        if(!keyResponse.isEmpty())
        {
            val trailerUrl = "https://www.youtube.com/embed/$keyResponse"
            val intent = Intent(this, TrailerActivity::class.java)
            intent.putExtra("TRAILER_URL", trailerUrl)
            startActivity(intent)
        }
    })



    updateFavoriteIcon()

    // Favorite button click listener
    favoriteButton.setOnClickListener {
        if (favoritesManager.getFavoriteMovies().any { it.id == movie.id }) {
            favoritesManager.removeFavorite(movie)
        //    Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show()
        } else {
            favoritesManager.addFavorite(movie)
                topLottieAnimation.isVisible = true
                Handler(Looper.getMainLooper()).postDelayed({
                    topLottieAnimation.isVisible = false
                }, 750)


          //  Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
        }
        updateFavoriteIcon()
    }
}

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {  Toast.makeText(this, "Arrow Down KEYCODE_DPAD_UP", Toast.LENGTH_SHORT).show()}
            KeyEvent.KEYCODE_DPAD_DOWN -> {finish()}
            KeyEvent.KEYCODE_ENTER -> {
                Toast.makeText(this, "Arrow Down Pressed", Toast.LENGTH_SHORT).show()
                true
            }
            KeyEvent.KEYCODE_BACK -> { finish() }
        }
        return super.dispatchKeyEvent(event)
    }

private fun updateFavoriteIcon() {
    val isFavorite = favoritesManager.getFavoriteMovies().any { it.id == movie.id }
    favoriteButton.setImageResource(
        if (isFavorite) R.drawable.ic_red_favorite_24 else R.drawable.ic_baseline_favorite_border_24
    )

    }
}
