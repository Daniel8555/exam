package com.example.cellcom_exam

import android.content.res.Resources.Theme
import android.os.Bundle
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

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.Theme_AppCompat)
        super.onCreate(savedInstanceState)

        val binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val movie = intent.getParcelableExtra<Movie>("movie") as Movie

        val title = binding.movieTitle
        val poster = binding.moviePoster
        val overview = binding.movieOverview

        title.text = movie.title
        overview.text = movie.overview
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .into(poster)
    }
}
