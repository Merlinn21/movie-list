package com.mandiri.assestment.movie_list.models.GenreModels

import com.google.gson.annotations.SerializedName

data class GenreList(
    @SerializedName("genres")
    val genres: List<Genre>
)
