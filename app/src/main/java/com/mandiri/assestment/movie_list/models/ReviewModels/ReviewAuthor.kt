package com.mandiri.assestment.movie_list.models.ReviewModels

import com.google.gson.annotations.SerializedName

data class ReviewAuthor(
    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("avatar_path")
    val avatar_path: String?,

    @SerializedName("rating")
    val rating: Double,
)
