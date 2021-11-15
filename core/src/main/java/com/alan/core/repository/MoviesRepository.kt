package com.alan.core.repository

import androidx.lifecycle.Observer
import com.alan.core.data.impl.MovieWebDS
import com.alan.core.data.wrappers.MovieEntity
import com.alan.core.data.wrappers.VideoResult
import com.alan.core.sys.di.components.DaggerDataSourceComponent
import com.alan.core.utils.Constants
import com.alan.core.utils.Status
import javax.inject.Inject

class MoviesRepository {

    init {
        DaggerDataSourceComponent.builder().build().inject(this)
    }
    @Inject lateinit var webDS: MovieWebDS

    fun getPopularMovies(page: Int, observer: Observer<List<MovieEntity>>, error: Observer<Status>){
        webDS.getPopularMovies(apiKey(), languague(), page, buildPopularSuccessObserver(observer) , error)
    }
    fun getTopRatedMovies(page: Int, observer: Observer<List<MovieEntity>>, error: Observer<Status>){
        webDS.getTopRatedMovies(apiKey(), languague(), page, buildTopRatedSuccessObserver(observer), error)
    }
    fun getRecommendations(page: Int, movieId: Int, observer: Observer<List<MovieEntity>>, error: Observer<Status>){

        webDS.getMoviesRecommedation(apiKey(), languague(), page, movieId, buildRecommendationSuccessObserver(movieId, observer), error)

    }
    fun getMoviesBySearch(query: String, page: Int, adult: Boolean, observer: Observer<List<MovieEntity>>, error: Observer<Status>){
        webDS.getMoviesBySearch(apiKey(), languague(), query,  page, adult, observer , error)
    }

    fun getVideoMovie(movieId: Int, observer: Observer<VideoResult>, error: Observer<Status>){
        webDS.getVideoMovie(apiKey(), languague(), movieId, observer, error)
    }

    //Local

    //Private functions
    private fun apiKey(): String = Constants.API_KEY
    private fun languague(): String = Constants.MOVIE_LANG

    //region :: OBSERVERS
    private fun buildPopularSuccessObserver(observer: Observer<List<MovieEntity>>): Observer<List<MovieEntity>> {
        return Observer {
            observer.onChanged(it)
        }
    }

    private fun buildTopRatedSuccessObserver(observer: Observer<List<MovieEntity>>): Observer<List<MovieEntity>> {
        return Observer {
            observer.onChanged(it)
        }
    }

    private fun buildRecommendationSuccessObserver(idReco: Int, observer: Observer<List<MovieEntity>>): Observer<List<MovieEntity>> {
        return Observer {
            observer.onChanged(it)
        }
    }
    //endregion
}