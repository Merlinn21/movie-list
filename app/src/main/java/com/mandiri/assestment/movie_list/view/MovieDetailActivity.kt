package com.mandiri.assestment.movie_list.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.global.URL
import com.mandiri.assestment.movie_list.models.MovieModels.MovieDetail
import com.mandiri.assestment.movie_list.models.ReviewModels.Review
import com.mandiri.assestment.movie_list.models.VideoModels.Video
import com.mandiri.assestment.movie_list.view_models.MovieViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var viewModel : MovieViewModel

    private lateinit var videoPlayer : YouTubePlayerView
    private lateinit var posterImageView : ImageView
    private lateinit var backdropImageView: ImageView
    private lateinit var originalTitleView : TextView
    private lateinit var releaseDateTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var runtimeTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var sampleReviewAuthorTextView: TextView
    private lateinit var sampleReviewScoreTextView: TextView
    private lateinit var sampleReviewContentTextView: TextView
    private lateinit var sampleReviewAvatarImageView: ImageView
    private lateinit var loadMoreTextView: TextView
    private lateinit var noReviewTextView: TextView
    private lateinit var layoutReview : View


    private var movieId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)

        initView()
        initExtra()
        initViewModel()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoPlayer.enterFullScreen();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            videoPlayer.exitFullScreen();
        }
    }

    override fun onBackPressed() {
        if (videoPlayer.isFullScreen())
            videoPlayer.exitFullScreen()
        else
            super.onBackPressed()
    }

    private fun initView(){
        backdropImageView = findViewById(R.id.iv_backdrop_poster)
        posterImageView = findViewById(R.id.iv_poster)
        originalTitleView = findViewById(R.id.tv_original_title)
        releaseDateTextView = findViewById(R.id.tv_release_date)
        statusTextView = findViewById(R.id.tv_status)
        runtimeTextView = findViewById(R.id.tv_runtime)
        scoreTextView = findViewById(R.id.tv_score)
        overviewTextView = findViewById(R.id.tv_overview)
        sampleReviewAuthorTextView = findViewById(R.id.tv_author_name)
        sampleReviewContentTextView = findViewById(R.id.tv_review_content)
        sampleReviewScoreTextView = findViewById(R.id.tv_star_score)
        sampleReviewAvatarImageView = findViewById(R.id.iv_avatar)
        noReviewTextView = findViewById(R.id.tv_no_review)
        loadMoreTextView = findViewById(R.id.tv_load_more)
        layoutReview = findViewById(R.id.layout_review)

        videoPlayer = findViewById(R.id.video_player)
        lifecycle.addObserver(videoPlayer)
    }

    private fun initExtra(){
        movieId = intent.getIntExtra("movieId", 0)
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.observeMovieDetailLiveData().observe(this) { movie ->
            if(movie.videos.results.isNotEmpty()){
                setYoutubeTrailer(movie.videos.results)
            } else{
                // If there is not video to be found
                setBackdropImage(movie)
            }

            initMovieDetail(movie)
        }

        viewModel.observeReviewLiveData().observe(this) { reviews ->
            if(reviews.reviewResult.isNotEmpty()){
                initSampleReview(reviews.reviewResult[0])
            } else{
                noReviewTextView.visibility = View.VISIBLE
                loadMoreTextView.visibility = View.GONE
                layoutReview.visibility = View.GONE
            }
        }

        viewModel.getMovieDetail(movieId)
        viewModel.getReview(movieId, 1)
    }

    private fun setYoutubeTrailer(videoList : List<Video>){
        var trailerVideo : Video? = null
        for (video in videoList){
            if(video.type.lowercase() == "trailer"){
                trailerVideo = video
                break
            }
        }

        if(trailerVideo == null){
            trailerVideo = videoList[0]
        }

        videoPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(trailerVideo.key, 0f)
            }
        })
    }

    private fun setBackdropImage(movieDetail : MovieDetail){
        Glide.with(this)
            .load(URL.BASE_IMG_URL + movieDetail.backdrop_path)
            .into(backdropImageView)

        backdropImageView.visibility = View.VISIBLE
        videoPlayer.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun initMovieDetail(movieDetail: MovieDetail){
        originalTitleView.text = movieDetail.title
        releaseDateTextView.text = movieDetail.release_date
        statusTextView.text = movieDetail.status
        scoreTextView.text = String.format("%.1f", movieDetail.vote_average)
        overviewTextView.text = movieDetail.overview
        runtimeTextView.text = movieDetail.runtime.toString() + "m"

        Glide.with(this)
            .load(URL.BASE_IMG_URL + movieDetail.poster_path)
            .into(posterImageView)
    }

    private fun initSampleReview(review : Review){
        layoutReview.visibility = View.VISIBLE
        loadMoreTextView.visibility = View.VISIBLE
        noReviewTextView.visibility = View.GONE

        sampleReviewScoreTextView.text = review.author_details.rating.toString()
        sampleReviewContentTextView.text = review.content
        sampleReviewAuthorTextView.text = review.author


        val avatarPath = review.author_details.avatar_path
        //Default Image for Gravatar
        var url = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?f=y"
        if(avatarPath != null){
            url = if(avatarPath.contains("https")){
                avatarPath.substring(1, avatarPath.length)
            } else{
                URL.BASE_AVATAR_IRL + avatarPath
            }
        }
        
        Glide.with(this)
            .load(url)
            .into(sampleReviewAvatarImageView)

        loadMoreTextView.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("movieId", movieId)
            startActivity(intent)
        })
    }
}