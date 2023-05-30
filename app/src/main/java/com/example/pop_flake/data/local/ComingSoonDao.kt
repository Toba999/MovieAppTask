package com.example.pop_flake.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pop_flake.data.pojo.ComingSoonMovie


@Dao
interface  ComingSoonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<ComingSoonMovie>)

    @Query("SELECT * FROM ComingSoonMovie ORDER BY title ASC LIMIT :pageSize OFFSET :offset")
    suspend fun getComingMoviesWithPagination(pageSize: Int, offset: Int):List<ComingSoonMovie>


}