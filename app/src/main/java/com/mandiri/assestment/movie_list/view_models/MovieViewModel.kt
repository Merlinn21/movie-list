package com.mandiri.assestment.movie_list.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.assestment.movie_list.api.RetrofitInstance
import com.mandiri.assestment.movie_list.models.GenreModels.GenreList
import com.mandiri.assestment.movie_list.models.MovieModels.DiscoveredMovie
import com.mandiri.assestment.movie_list.models.MovieModels.MovieDetail
import com.mandiri.assestment.movie_list.models.ReviewModels.ReviewsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private var movieLiveData = MutableLiveData<DiscoveredMovie>()
    private var movieGenreData = MutableLiveData<GenreList>()
    private var movieDetailData = MutableLiveData<MovieDetail>()
    private var reviewData = MutableLiveData<ReviewsList>()

    fun getMovieGenres(){
        RetrofitInstance.apiService.getGenreList(RetrofitInstance.API_KEY).enqueue(object :
            Callback<GenreList> {
                override fun onResponse(call: Call<GenreList>, response: Response<GenreList>) {

                    if (response.body() != null){
                        movieGenreData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<GenreList>, t: Throwable) {
                    println("OnFail: ${t.localizedMessage}")
                }
            }
        )
    }

    fun getPopularMovies(genre : String, page : Int) {
        RetrofitInstance.apiService.getMovieList(RetrofitInstance.API_KEY, genre, page).enqueue(object  :
            Callback<DiscoveredMovie> {
                override fun onResponse(call: Call<DiscoveredMovie>, response: Response<DiscoveredMovie>) {
                    if (response.body() != null){
                        movieLiveData.value = response.body()
                    }
                }
                override fun onFailure(call: Call<DiscoveredMovie>, t: Throwable) {
                    println("OnFail: ${t.localizedMessage}")
                }
            }
        )
    }

    fun getMovieDetail(id: Int){
        val appendRespond = "videos"
        RetrofitInstance.apiService.getMovieDetail(id, RetrofitInstance.API_KEY, appendRespond).enqueue(object  :
            Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.body() != null){
                    movieDetailData.value = response.body()
                }
            }
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        }
        )
    }

    fun getReview(id: Int, page : Int){
        RetrofitInstance.apiService.getReviews(id, RetrofitInstance.API_KEY, page).enqueue(object  :
            Callback<ReviewsList> {
            override fun onResponse(call: Call<ReviewsList>, response: Response<ReviewsList>) {
                if (response.body() != null){
                    reviewData.value = response.body()
                }
            }
            override fun onFailure(call: Call<ReviewsList>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun observeMovieLiveData() : LiveData<DiscoveredMovie> {
        return movieLiveData
    }

    fun observeGenreLiveData() : LiveData<GenreList> {
        return movieGenreData
    }

    fun observeMovieDetailLiveData() : LiveData<MovieDetail>{
        return movieDetailData
    }

    fun observeReviewLiveData(): LiveData<ReviewsList>{
        return reviewData
    }
}