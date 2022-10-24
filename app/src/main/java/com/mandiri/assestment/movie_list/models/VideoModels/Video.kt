package com.mandiri.assestment.movie_list.models.VideoModels

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("name")
    val name: String,

    @SerializedName("key")
    val key: String,

    @SerializedName("site")
    val site: String,

    @SerializedName("type")
    val type: String,
)
