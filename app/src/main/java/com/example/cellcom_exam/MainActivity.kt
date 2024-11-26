package com.example.cellcom_exam

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.example.cellcom_exam.databinding.MainActivityBinding



class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val favoritesManager by lazy { FavoritesManager.initialize(this) }
    private val movieApiService by lazy {
        ApiService.createService(MovieApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPopularMovies()

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {

            KeyEvent.KEYCODE_DPAD_LEFT -> {
            openOptionsMenu()
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }


    //menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
//menu selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_popular -> fetchPopularMovies()
            R.id.action_now_playing -> fetchNowPlayingMovies()
            R.id.action_favorites -> fetchFavoriteMovies()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchPopularMovies() {
        val apiKey = ApiService.API_KEY
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = movieApiService.getPopularMovies(apiKey)
                movieAdapter = MovieAdapter(this@MainActivity, response.results, favoritesManager)
                recyclerView.adapter = movieAdapter
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching movies", e)
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        val apiKey =  ApiService.API_KEY
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = movieApiService.getNowPlayingMovies(apiKey)
                movieAdapter = MovieAdapter(this@MainActivity, response.results, favoritesManager)
                recyclerView.adapter = movieAdapter
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching movies", e)
            }
        }
    }

    private fun fetchFavoriteMovies() {
        val favoriteMovies = FavoritesManager.getFavoriteMovies()
        movieAdapter = MovieAdapter(this, favoriteMovies, favoritesManager)
        recyclerView.adapter = movieAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
