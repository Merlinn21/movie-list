package com.mandiri.assestment.movie_list.models.ReviewModels

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author")
    val author: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("author_details")
    val author_details: ReviewAuthor,

    var isClicked : Boolean = false
)
