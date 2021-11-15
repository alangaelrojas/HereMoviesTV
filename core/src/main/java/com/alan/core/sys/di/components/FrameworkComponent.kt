package com.alan.core.sys.di.components

import com.alan.core.data.impl.MovieWebDS
import com.alan.core.sys.di.modules.FrameworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FrameworkModule::class])
interface FrameworkComponent {

    fun inject(moviesWebDS: MovieWebDS)
}