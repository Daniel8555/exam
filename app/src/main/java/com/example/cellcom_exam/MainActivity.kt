package com.example.cellcom_exam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cellcom_exam.ui.theme.Cellcom_examTheme
import com.example.cellcom_exam.databinding.MainActivityBinding
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val movies = ApiService.getPopularMovies()
            adapter = MovieAdapter(movies) { movie ->
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movie)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }


    }
}


