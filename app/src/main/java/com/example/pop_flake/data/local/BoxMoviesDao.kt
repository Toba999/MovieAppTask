package com.example.pop_flake.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pop_flake.data.pojo.BoxOfficeMovie
import kotlinx.coroutines.flow.Flow


@Dao
interface  BoxMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<BoxOfficeMovie>)

    @Query("SELECT * FROM BoxOfficeMovie ORDER BY title")
    suspend fun getMovies(): List<BoxOfficeMovie>


}