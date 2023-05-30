package com.example.pop_flake.data.repository

import com.example.pop_flake.data.pojo.BoxOfficeResponse
import com.example.pop_flake.data.pojo.ComingSoonResponse
import com.example.pop_flake.data.pojo.InTheaterResponse
import com.example.pop_flake.data.pojo.SearchResponse
import com.example.pop_flake.data.pojo.TopMoviesResponse
import com.example.pop_flake.data.remote.ResponseState
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getTopRatedMovies(token: String): Flow<ResponseState<TopMoviesResponse>>
    suspend fun getInTheatersMovies(token: String): Flow<ResponseState<InTheaterResponse>>
    suspend fun getComingSoonMovies(token: String): Flow<ResponseState<ComingSoonResponse>>
    suspend fun getBoxOfficeMovies(token: String): Flow<ResponseState<BoxOfficeResponse>>
    suspend fun searchMovies(query: String, token: String): Flow<ResponseState<SearchResponse>>
}