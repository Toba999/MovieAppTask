package com.example.pop_flake.data.pojo

data class SearchResponse(
    val errorMessage: String,
    val expression: String,
    val results: List<MovieResult>,
    val searchType: String
)