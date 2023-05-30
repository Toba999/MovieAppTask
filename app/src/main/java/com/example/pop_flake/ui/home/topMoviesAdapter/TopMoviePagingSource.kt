package com.example.pop_flake.ui.home.topMoviesAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.local.TopMoviesDao
import com.example.pop_flake.data.pojo.TopRatedMovie
import com.example.pop_flake.data.repository.Repo
import javax.inject.Inject
import javax.inject.Named


class TopMoviePagingSource @Inject constructor(private val database: MovieDatabase) : PagingSource<Int, TopRatedMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopRatedMovie> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val movies = database.topMoviesDao().getTopMoviesWithPagination(offset = (page - 1) * pageSize, pageSize = pageSize)
            LoadResult.Page(
                data = movies,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (movies.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopRatedMovie>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}
