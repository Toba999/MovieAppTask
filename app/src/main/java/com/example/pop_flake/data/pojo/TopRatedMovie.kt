package com.example.pop_flake.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TopMovies")
data class TopRatedMovie(
    val crew: String? = "",
    val fullTitle: String? = "",
    @PrimaryKey val id: String = "-1",
    val imDbRating: String? = "",
    val imDbRatingCount: String? = "",
    val image: String? = "",
    val rank: String? = "",
    val title: String? = "",
    val year: String? = "",
    val isShimmer: Boolean = false
)