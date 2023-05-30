package com.example.pop_flake.data.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComplaintModel(
    val title: String,
    val description: String
) : Parcelable