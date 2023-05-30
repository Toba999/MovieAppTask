package com.example.pop_flake.data.pojo

data class TopMoviesResponse(
    val errorMessage: String,
    val items: List<TopRatedMovie>
)