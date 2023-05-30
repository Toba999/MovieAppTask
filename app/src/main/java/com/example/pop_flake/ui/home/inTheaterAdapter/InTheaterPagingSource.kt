package com.example.pop_flake.ui.home.inTheaterAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pop_flake.data.local.InTheaterDao
import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.pojo.InTheaterMovie
import javax.inject.Inject

class InTheaterPagingSource @Inject constructor(private val database: MovieDatabase) : PagingSource<Int, InTheaterMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InTheaterMovie> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val movies = database.inTheaterMovieDao().getTheaterMoviesWithPagination(offset = (page - 1) * pageSize, pageSize = pageSize)
            LoadResult.Page(
                data = movies,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (movies.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, InTheaterMovie>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}
