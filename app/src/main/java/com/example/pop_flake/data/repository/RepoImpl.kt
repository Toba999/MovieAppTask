package com.example.pop_flake.data.repository

import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.pojo.BoxOfficeResponse
import com.example.pop_flake.data.pojo.ComingSoonResponse
import com.example.pop_flake.data.pojo.InTheaterResponse
import com.example.pop_flake.data.pojo.SearchResponse
import com.example.pop_flake.data.pojo.TopMoviesResponse
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class RepoImpl (private val apiService: ApiService,private val database: MovieDatabase) : Repo {

        override  fun getTopRatedMovies(token: String): Flow<ResponseState<TopMoviesResponse>> {
            return flow {
                emit(ResponseState.Loading)
                val response = apiService.getTopRatedMovies(token)
                if (response.isSuccessful) {
                    val movies = response.body()
                    if (movies != null) {
                        database.topMoviesDao().insertMovies(movies.items)
                        emit(ResponseState.Success(movies))
                    } else {
                        emit(ResponseState.Error("No movies found"))
                    }
                } else {
                    emit(ResponseState.Error(response.message()))
                }
            }
        }

        override  fun getInTheatersMovies(token: String): Flow<ResponseState<InTheaterResponse>> {
            return flow {
                emit(ResponseState.Loading)
                val response = apiService.getInTheatersMovies(token)
                if (response.isSuccessful) {
                    val movies = response.body()
                    if (movies != null) {
                        database.inTheaterMovieDao().insertMovies(movies.items)
                        emit(ResponseState.Success(movies))
                    } else {
                        emit(ResponseState.Error("No movies found"))
                    }
                } else {
                    emit(ResponseState.Error(response.message()))
                }
            }
        }

        override fun getComingSoonMovies(token: String): Flow<ResponseState<ComingSoonResponse>> {
            return flow {
                emit(ResponseState.Loading)
                val response = apiService.getComingSoonMovies(token)
                if (response.isSuccessful) {
                    val movies = response.body()
                    if (movies != null) {
                        database.comingSoonMovieDao().insertMovies(movies.items)
                        emit(ResponseState.Success(movies))
                    } else {
                        emit(ResponseState.Error("No movies found"))
                    }
                } else {
                    emit(ResponseState.Error(response.message()))
                }
            }
        }

    override fun getBoxOfficeMovies(token: String): Flow<ResponseState<BoxOfficeResponse>> {
        return flow {
            emit(ResponseState.Loading)
            val response = apiService.getBoxOfficeMovies(token)
            if (response.isSuccessful) {
                val movies = response.body()
                if (movies != null) {
                    emit(ResponseState.Success(movies))
                } else {
                    emit(ResponseState.Error("No movies found"))
                }
            } else {
                emit(ResponseState.Error(response.message()))
            }
        }
    }

    override fun searchMovies(query: String,token: String): Flow<ResponseState<SearchResponse>> {
            return flow {
                emit(ResponseState.Loading)
                val response = apiService.getSearchMovies(token, query)
                if (response.isSuccessful) {
                    val searchResponse = response.body()!!
                    emit(ResponseState.Success(searchResponse))
                } else {
                    emit(ResponseState.Error(response.message()))
                }
            }
        }
}

