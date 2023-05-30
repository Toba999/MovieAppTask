package com.example.pop_flake.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "BoxOfficeMovie")
data class BoxOfficeMovie(
    val gross: String? = "",
    @PrimaryKey val id: String = "-1",
    val image: String? = "",
    val rank: String? = "",
    val title: String? = "",
    val weekend: String? = "",
    val weeks: String? = "",
    var isShimmer: Boolean
)