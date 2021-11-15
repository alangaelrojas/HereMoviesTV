package com.alan.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alan.core.data.wrappers.MovieEntity
import com.alan.core.data.database.dao.MoviesDao

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}