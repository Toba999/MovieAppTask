package com.example.pop_flake.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ComingSoonMovie")
data class ComingSoonMovie(
    val contentRating: String?,
    val directors: String?,
    val fullTitle: String?,
    val genres: String?,
    @PrimaryKey val id: String,
    val imDbRating: String?,
    val imDbRatingCount: String?,
    val image: String?,
    val metacriticRating: String?,
    val plot: String?,
    val releaseState: String?,
    val runtimeMins: String?,
    val runtimeStr: String?,
    val stars: String?,
    val title: String?,
    val year: String?
)