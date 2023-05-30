package com.example.pop_flake.data.remote

import com.example.pop_flake.data.pojo.BoxOfficeResponse
import com.example.pop_flake.data.pojo.ComingSoonResponse
import com.example.pop_flake.data.pojo.InTheaterResponse
import com.example.pop_flake.data.pojo.SearchResponse
import com.example.pop_flake.data.pojo.TopMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("Top250Movies/{apiKey}")
    suspend fun getTopRatedMovies(@Path("apiKey") token: String): Response<TopMoviesResponse>

    @GET("InTheaters/{apiKey}")
    suspend fun getInTheatersMovies(@Path("apiKey") token: String): Response<InTheaterResponse>

    @GET("ComingSoon/{apiKey}")
    suspend fun getComingSoonMovies(@Path("apiKey") token: String): Response<ComingSoonResponse>

    @GET("BoxOffice/{apiKey}")
    suspend fun getBoxOfficeMovies(@Path("apiKey") token: String): Response<BoxOfficeResponse>

    @GET("Search/{apiKey}/{query}")
    suspend fun getSearchMovies(@Path("apiKey") token: String,@Path("query") query: String): Response<SearchResponse>
}