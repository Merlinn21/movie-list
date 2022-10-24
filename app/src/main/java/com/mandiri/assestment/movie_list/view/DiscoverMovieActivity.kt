package com.mandiri.assestment.movie_list.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.adapters.MovieAdapter
import com.mandiri.assestment.movie_list.item_decoration.GridItemDecoration
import com.mandiri.assestment.movie_list.models.MovieModels.Movie
import com.mandiri.assestment.movie_list.view_models.MovieViewModel

class DiscoverMovieActivity : AppCompatActivity() {
    lateinit var viewModel : MovieViewModel

    lateinit var movieRecyclerView : RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var titleText : TextView

    private val movieAdapter = MovieAdapter { position -> onMovieClick(position) }

    private var genreName : String? = null
    private var genreId : String? = null

    private var page : Int = 1
    private var isLoading : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_movie)

        initExtra()
        initView()
        initMovie()
        initViewModel()
    }

    private fun initView(){
        progressBar = findViewById(R.id.my_progress)
        movieRecyclerView = findViewById(R.id.rc_movie)
        titleText = findViewById(R.id.tv_title)

        titleText.text = genreName
    }

    private fun initExtra(){
        genreName = intent.getStringExtra("genreName")
        genreId = intent.getStringExtra("genreId")
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.observeMovieLiveData().observe(this) { movieList ->
            if (isLoading) {
                isLoading = false
                movieRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            movieAdapter.setGenreList(movieList.results)
        }
        viewModel.getPopularMovies(genreId!!, page)
    }

    private fun initMovie(){
        val gridLayoutManager = GridLayoutManager(this, 2)
        val gridItemDecoration = GridItemDecoration(2,
            resources.getDimension(R.dimen.normal_margin_padding).toInt(), true)

        movieRecyclerView.addItemDecoration(gridItemDecoration)
        movieRecyclerView.adapter = movieAdapter
        movieRecyclerView.layoutManager = gridLayoutManager
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItem = gridLayoutManager.findLastVisibleItemPosition()

                if(lastItem > movieAdapter.itemCount / 2 && !isLoading){
                    isLoading = true
                    page++
                    viewModel.getPopularMovies(genreId!!, page)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun onMovieClick(position: Int){
        val data : Movie = movieAdapter.getMovie(position)

        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movieId", data.id)
        startActivity(intent)
    }
}