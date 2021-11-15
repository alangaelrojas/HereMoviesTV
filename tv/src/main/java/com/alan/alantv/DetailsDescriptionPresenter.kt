package com.alan.alantv

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.alan.core.data.wrappers.MovieEntity

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
            viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
            item: Any) {
        val movie = item as MovieEntity

        viewHolder.title.text = movie.title
        viewHolder.subtitle.text = movie.type
        viewHolder.body.text = movie.overview
    }
}