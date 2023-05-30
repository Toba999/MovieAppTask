package com.example.pop_flake.data.pojo

data class BoxOfficeResponse(
    val errorMessage: String,
    val items: List<BoxOfficeMovie>
)