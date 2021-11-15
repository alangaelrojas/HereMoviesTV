package com.alan.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.alan.core.data.wrappers.MovieEntity


@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    fun insertMovies(article: MovieEntity)

    @Query("select * from movie where type=:type order by popularity desc limit 100")
    fun getMovies(type: String): List<MovieEntity>

    @Query("select * from movie where title like :title order by popularity desc limit 100")
    fun getMoviesBySearch(title: String): List<MovieEntity>

    @Query("select * from movie where type=:type and recoId = :recoId order by popularity desc limit 100")
    fun getMoviesRecommendaion(type: String, recoId: Int): List<MovieEntity>


}