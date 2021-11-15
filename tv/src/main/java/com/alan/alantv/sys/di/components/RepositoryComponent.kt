package com.alan.alantv.sys.di.components

import com.alan.alantv.app.viewmodels.MoviesViewModel
import com.alan.core.sys.di.modules.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun inject(moviesViewModel: MoviesViewModel)
    //fun inject(detailMovieViewModel: DetailMovieViewModel)
}