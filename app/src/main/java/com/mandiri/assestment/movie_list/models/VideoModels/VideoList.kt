package com.mandiri.assestment.movie_list.models.VideoModels

import com.google.gson.annotations.SerializedName

data class VideoList(
    @SerializedName("results")
    val results: List<Video>
)
