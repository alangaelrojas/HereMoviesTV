package com.alan.core.data

import com.alan.core.data.wrappers.MoviesResponse
import com.alan.core.data.wrappers.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("search/movie")
    fun getMoviesBySearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
    ): Call<MoviesResponse>

    @GET("movie/{movieId}/recommendations")
    fun getMoviesRecommendationByMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int

    ): Call<MoviesResponse>

    @GET("movie/{movieId}/videos")
    fun getVideoMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Call<VideoResponse>
}