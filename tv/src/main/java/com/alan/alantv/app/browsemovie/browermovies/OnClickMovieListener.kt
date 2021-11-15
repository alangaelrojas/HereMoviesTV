package com.alan.alantv.app.browsemovie.browermovies

import android.widget.ImageView
import com.alan.core.data.wrappers.MovieEntity

interface OnClickMovieListener {

    fun onClickMovie(movie: MovieEntity, movieImageView: ImageView)
}