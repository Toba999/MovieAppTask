package com.example.pop_flake.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pop_flake.data.pojo.InTheaterMovie


@Dao
interface  InTheaterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<InTheaterMovie>)

    @Query("SELECT * FROM InTheaterMovie ORDER BY title ASC LIMIT :pageSize OFFSET :offset")
    suspend fun getTheaterMoviesWithPagination(pageSize: Int, offset: Int): List<InTheaterMovie>


}