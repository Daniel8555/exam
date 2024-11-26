package com.example.cellcom_exam

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView


class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val favoritesManager: Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

        holder.favoriteButton.setImageResource(
            if (FavoritesManager.getFavoriteMovies().any { it.id == movie.id }) {
                R.drawable.ic_red_favorite_24

            }
            else
                R.drawable.ic_baseline_favorite_border_24
        )



        holder.favoriteButton.setOnClickListener {
            if (FavoritesManager.getFavoriteMovies().any { it.id == movie.id }) {
                FavoritesManager.removeFavorite(movie)
                holder.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            } else {
                FavoritesManager.addFavorite(movie)
                holder.favoriteButton.setImageResource(R.drawable.ic_red_favorite_24)
               val lottie =  (context as Activity).findViewById<LottieAnimationView>(R.id.topLottieAnimation)
                val recycle = (context as Activity).findViewById<RecyclerView>(R.id.recyclerView)
                recycle.isVisible = false
                lottie.isVisible = true
                Handler(Looper.getMainLooper()).postDelayed({
                    lottie.isVisible = false
                    recycle.isVisible = true
                }, 750)
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)

            intent.putExtra("movie_key", movie)
            intent.putExtra("isFavorite",movie.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.movieTitle)
        private val poster: ImageView = itemView.findViewById(R.id.moviePoster)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.fav_btn)
        private val details: TextView = itemView.findViewById(R.id.decription)

        fun bind(movie: Movie) {
            title.text = movie.title
            details.text = movie.overview

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(poster)
        }
    }
}
