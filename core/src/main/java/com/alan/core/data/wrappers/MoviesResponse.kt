package com.alan.core.data.wrappers

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page : Int,
    @SerializedName("results") val movies : List<MovieEntity>,
    @SerializedName("total_pages") val totalPages : Int,
    @SerializedName("total_results") val totalResults : Int
)
