package com.mandiri.assestment.movie_list.models.MovieModels

import com.google.gson.annotations.SerializedName

data class DiscoveredMovie (
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<Movie>,

    @SerializedName("total_results")
    val total_result: Int,

    @SerializedName("total_pages")
    val total_pages: Int
)