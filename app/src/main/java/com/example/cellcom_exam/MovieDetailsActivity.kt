package com.example.cellcom_exam

import android.content.res.Resources.Theme
import android.os.Bundle
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.Glide
import com.example.cellcom_exam.databinding.ActivityMovieDetailsBinding
import com.example.cellcom_exam.ui.theme.Cellcom_examTheme


//   Glide.with(this)
//            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
//            .into(poster)
//    }
class MovieDetailsActivity : AppCompatActivity() {
private lateinit var favoritesManager: FavoritesManager
private lateinit var favoriteButton: ImageButton
private lateinit var movie: Movie

override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
        val binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


    movieTitle.text = movie.title
    movieDescription.text = movie.overview
       Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .into(moviePoster)
    movieDescription.setOnClickListener(View.OnClickListener {

    })



    updateFavoriteIcon()

    // Favorite button click listener
    favoriteButton.setOnClickListener {
        if (favoritesManager.getFavoriteMovies().any { it.id == movie.id }) {
            favoritesManager.removeFavorite(movie)
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show()
        } else {
            favoritesManager.addFavorite(movie)
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
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
