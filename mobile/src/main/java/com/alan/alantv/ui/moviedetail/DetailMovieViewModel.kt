package com.alan.alantv.ui.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alan.alantv.sys.di.components.DaggerRepositoryComponent
import com.alan.core.data.wrappers.MovieEntity
import com.alan.core.data.wrappers.VideoResult
import com.alan.core.repository.MoviesRepository
import com.alan.core.utils.Status
import javax.inject.Inject

class DetailMovieViewModel : ViewModel() {

    init {
        DaggerRepositoryComponent.create().inject(this)
    }
    @Inject
    lateinit var repository: MoviesRepository

    //region :: VALS
    private var pageCountRecommendations = 1
    //endregion

    //region :: LIVEDATAS
    val recommendations by lazy { MutableLiveData<List<MovieEntity>>() }
    val errorRecommendations by lazy { MutableLiveData<Status>() }
    val movie by lazy { MutableLiveData<MovieEntity>() }
    val video by lazy { MutableLiveData<VideoResult>() }
    val errorVideo by lazy { MutableLiveData<Status>() }
    //endregion

    fun getRecommendations(movieId: Int){
        repository.getRecommendations(pageCountRecommendations, movieId, buildRecommendationsSuccessObserver(), errorRecommendationsObserver())
    }
    fun setMovie(movie: MovieEntity){
        this.movie.value = movie
    }
    fun getVideo(movieId: Int){
        repository.getVideoMovie(movieId, buildVideoSuccessObserver(), errorVideoObserver())
    }

    //region :: OBSERVERS
    private fun buildRecommendationsSuccessObserver(): Observer<List<MovieEntity>> {
        return Observer {
            if (it.isNotEmpty()) recommendations.value = it.sortedByDescending { list -> list.popularity }
        }
    }
    private fun errorRecommendationsObserver(): Observer<Status> {
        return Observer {
            errorRecommendations.value = it
        }
    }

    private fun buildVideoSuccessObserver(): Observer<VideoResult> {
        return Observer {
            if (it != null ) video.value = it
            else errorVideo.value  = Status(404, "Video unavailable")
        }
    }

    private fun errorVideoObserver(): Observer<Status> {
        return Observer {
            errorVideo.value  = it
        }
    }
    //endregion
}