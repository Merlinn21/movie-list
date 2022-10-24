package com.mandiri.assestment.movie_list.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.global.URL
import com.mandiri.assestment.movie_list.models.MovieModels.Movie
import java.util.Collections.emptyList

class MovieAdapter (private val onMovieClick: OnMovieClick): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    private var moviesData : MutableList<Movie> = emptyList<Movie?>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_movie, parent, false)

        return ViewHolder(view, onMovieClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movieData = moviesData[position]

        holder.titleText.text = movieData.title

        Glide.with(holder.itemView)
            .load(URL.BASE_IMG_URL + movieData.poster_path)
            .into(holder.moviePosterImage)
    }

    override fun getItemCount(): Int {
        return moviesData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setGenreList(moviesData : List<Movie>){
        val startSize = this.moviesData.size
        this.moviesData.addAll(moviesData)
        val newSize = this.moviesData.size

        notifyItemRangeInserted(startSize, newSize)
    }

    fun getMovie(position: Int) : Movie {
        return moviesData[position]
    }

    class ViewHolder(itemView: View, onMovieClick: OnMovieClick) : RecyclerView.ViewHolder(itemView) {
        val titleText : TextView = itemView.findViewById(R.id.tv_movie_title)
        val moviePosterImage : ImageView = itemView.findViewById(R.id.iv_movie_poster)

        init {
            moviePosterImage.setOnClickListener {
                onMovieClick.onMovieClick(absoluteAdapterPosition)
            }
        }
    }

    fun interface OnMovieClick {
        fun onMovieClick(position: Int)
    }
}