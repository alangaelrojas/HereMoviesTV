package com.alan.core.data.wrappers

import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("id") val id: String, // "5c9b83b89251416b0cf835bf",
    @SerializedName("key") val key: String, // "se9n853lBNo",
    @SerializedName("name") val name: String, // "Fast Color Movie Trailer",
    @SerializedName("site") val site: String, // "YouTube"
)
