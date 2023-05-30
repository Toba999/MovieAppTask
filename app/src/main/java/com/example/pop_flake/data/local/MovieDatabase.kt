package com.example.pop_flake.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pop_flake.data.pojo.BoxOfficeMovie
import com.example.pop_flake.data.pojo.ComingSoonMovie
import com.example.pop_flake.data.pojo.InTheaterMovie
import com.example.pop_flake.data.pojo.TopRatedMovie

@Database(entities = [
    TopRatedMovie::class,
    ComingSoonMovie::class,
    InTheaterMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun topMoviesDao(): TopMoviesDao
    abstract fun comingSoonMovieDao(): ComingSoonDao
    abstract fun inTheaterMovieDao(): InTheaterDao
}
