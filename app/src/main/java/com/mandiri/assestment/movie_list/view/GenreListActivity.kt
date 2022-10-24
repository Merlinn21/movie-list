package com.mandiri.assestment.movie_list.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.adapters.GenreAdapter
import com.mandiri.assestment.movie_list.item_decoration.GridItemDecoration
import com.mandiri.assestment.movie_list.view_models.MovieViewModel

class GenreListActivity : AppCompatActivity() {
    private lateinit var viewModel : MovieViewModel
    private lateinit var genreRecyclerView : RecyclerView
    private lateinit var progressBar : ProgressBar

    private val genreAdapter = GenreAdapter { position -> onGenreClick(position) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        initView()
        initGenre()
        initViewModel()
    }

    private fun initView(){
        progressBar = findViewById(R.id.my_progress)
        genreRecyclerView = findViewById(R.id.rc_genres)
    }

    private fun initGenre() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        val gridItemDecoration = GridItemDecoration(2,
            resources.getDimension(R.dimen.normal_margin_padding).toInt(), true)

        genreRecyclerView.addItemDecoration(gridItemDecoration)
        genreRecyclerView.adapter = genreAdapter
        genreRecyclerView.layoutManager = gridLayoutManager
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.observeGenreLiveData().observe(this, Observer { genreList ->
            progressBar.visibility = View.GONE
            genreRecyclerView.visibility = View.VISIBLE

            genreAdapter.setGenreList(genreList.genres)
        })

        viewModel.getMovieGenres()
    }

    private fun onGenreClick(position: Int){
        val intent = Intent(this, DiscoverMovieActivity::class.java)
        intent.putExtra("genreId", genreAdapter.getItem(position).id.toString())
        intent.putExtra("genreName", genreAdapter.getItem(position).name)
        startActivity(intent)
    }
}