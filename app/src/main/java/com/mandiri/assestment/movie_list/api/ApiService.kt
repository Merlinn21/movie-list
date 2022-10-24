package com.mandiri.assestment.movie_list.api
import com.mandiri.assestment.movie_list.models.MovieModels.DiscoveredMovie
import com.mandiri.assestment.movie_list.models.GenreModels.GenreList
import com.mandiri.assestment.movie_list.models.MovieModels.MovieDetail
import com.mandiri.assestment.movie_list.models.ReviewModels.ReviewsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?")
    fun getMovieList(
        @Query("api_key") api_key : String,
        @Query("with_genres") with_genres : String,
        @Query("page") page : Int
    ) : Call<DiscoveredMovie>

    @GET("genre/movie/list?")
    fun getGenreList(@Query("api_key") api_key: String) : Call<GenreList>

    @GET("movie/{movie_id}?")
    fun getMovieDetail(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response : String
        ) :Call<MovieDetail>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key: String,
        @Query("page") page : Int
    ) : Call<ReviewsList>
}