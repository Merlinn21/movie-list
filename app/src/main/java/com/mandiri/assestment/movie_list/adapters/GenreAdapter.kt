package com.mandiri.assestment.movie_list.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.models.GenreModels.Genre

class GenreAdapter(private val onGenreItemClick: OnGenreItemClick) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var genresData : List<Genre> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_genre, parent, false)

        return ViewHolder(view, onGenreItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = genresData[position]

        holder.genreText.text = data.name
    }

    override fun getItemCount(): Int {
        return genresData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGenreList(genresData : List<Genre>){
        this.genresData = genresData
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : Genre {
        return genresData[position]
    }

    class ViewHolder(itemView: View, onGenreItemClick: OnGenreItemClick) : RecyclerView.ViewHolder(itemView) {
        val genreText : TextView = itemView.findViewById(R.id.tv_genre)
        val genreCard : CardView = itemView.findViewById(R.id.cv_genre)

        init {
            genreCard.setOnClickListener{
                onGenreItemClick.onGenreClick(adapterPosition)
            }
        }
    }

    fun interface OnGenreItemClick {
        fun onGenreClick(position: Int)
    }

}