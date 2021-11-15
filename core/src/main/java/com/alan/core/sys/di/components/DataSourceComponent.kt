package com.alan.core.sys.di.components

import com.alan.core.repository.MoviesRepository
import com.alan.core.sys.di.modules.DataSourceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class])
interface DataSourceComponent {

    fun inject(repository: MoviesRepository)
}