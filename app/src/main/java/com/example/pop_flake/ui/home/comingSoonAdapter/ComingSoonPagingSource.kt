package com.example.pop_flake.ui.home.comingSoonAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pop_flake.data.local.ComingSoonDao
import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.pojo.ComingSoonMovie
import javax.inject.Inject

class ComingSoonPagingSource @Inject constructor(private val database: MovieDatabase) : PagingSource<Int, ComingSoonMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComingSoonMovie> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val movies = database.comingSoonMovieDao().getComingMoviesWithPagination(offset = (page - 1) * pageSize, pageSize = pageSize)
            LoadResult.Page(
                data = movies,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (movies.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ComingSoonMovie>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}
