package com.alan.alantv.ui.movies

import android.widget.ImageView
import com.alan.core.data.wrappers.MovieEntity

interface OnClickMovieListener {

    fun onClickMovie(movie: MovieEntity, movieImageView: ImageView)
}