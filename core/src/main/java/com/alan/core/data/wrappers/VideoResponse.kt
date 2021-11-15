package com.alan.core.data.wrappers

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("results") val videos : List<VideoResult>,
)
