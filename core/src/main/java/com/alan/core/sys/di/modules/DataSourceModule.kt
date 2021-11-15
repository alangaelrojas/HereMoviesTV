package com.alan.core.sys.di.modules

import com.alan.core.data.impl.MovieWebDS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providesMoviesWebDS(): MovieWebDS = MovieWebDS()


}