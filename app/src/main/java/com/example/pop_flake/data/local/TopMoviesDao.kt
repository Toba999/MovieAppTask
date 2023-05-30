package com.example.pop_flake.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pop_flake.data.pojo.TopRatedMovie

@Dao
interface  TopMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<TopRatedMovie>)

    @Query("SELECT * FROM TopMovies ORDER BY title ASC LIMIT :pageSize OFFSET :offset")
    suspend fun getTopMoviesWithPagination(pageSize: Int, offset: Int):List<TopRatedMovie>


}