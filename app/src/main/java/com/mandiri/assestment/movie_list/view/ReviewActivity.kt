package com.mandiri.assestment.movie_list.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.adapters.ReviewAdapter
import com.mandiri.assestment.movie_list.view_models.MovieViewModel

class ReviewActivity : AppCompatActivity() {
    private lateinit var viewModel : MovieViewModel

    lateinit var reviewsRecyclerView : RecyclerView
    lateinit var progressBar : ProgressBar

    private val reviewAdapter = ReviewAdapter { position -> onReviewClick(position)}

    private var movieId : Int = 0
    private var page : Int = 1
    private var isLoading : Boolean = true
    private var isLastPage : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        initExtra()
        initView()
        initReviews()
        initViewModel()
    }

    private fun initExtra(){
        movieId = intent.getIntExtra("movieId", 0)
    }

    private fun initView(){
        reviewsRecyclerView = findViewById(R.id.rc_reviews)
        progressBar = findViewById(R.id.my_progress)
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.observeReviewLiveData().observe(this) { reviews ->
            if (isLoading) {
                isLoading = false
                reviewsRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE

                if(reviews.total_results == reviewAdapter.itemCount){
                    isLastPage = true
                }
            }

            reviewAdapter.setReviewData(reviews.reviewResult)
        }

        viewModel.getReview(movieId, page)
    }

    private fun initReviews(){
        val linearLayoutManager = LinearLayoutManager(this)
        val animator: RecyclerView.ItemAnimator = reviewsRecyclerView.itemAnimator!!

        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        reviewsRecyclerView.adapter = reviewAdapter
        reviewsRecyclerView.layoutManager = linearLayoutManager
        reviewsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItem = linearLayoutManager.findLastVisibleItemPosition()

                if(lastItem > reviewAdapter.itemCount / 2 && !isLoading && !isLastPage){
                    isLoading = true
                    page++
                    viewModel.getReview(movieId, page)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun onReviewClick(position : Int){
        val review = reviewAdapter.getReviewData(position)

        review.isClicked = !review.isClicked

        reviewAdapter.notifyItemChanged(position)
    }
}