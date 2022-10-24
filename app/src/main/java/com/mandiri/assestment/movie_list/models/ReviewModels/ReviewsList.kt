package com.mandiri.assestment.movie_list.models.ReviewModels

import com.google.gson.annotations.SerializedName

data class ReviewsList (
    @SerializedName("total_pages")
    val total_pages: Int,

    @SerializedName("total_results")
    val total_results: Int,

    @SerializedName("results")
    val reviewResult : List<Review>
)