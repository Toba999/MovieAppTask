package com.example.pop_flake.data.pojo

data class SettingsState(
    val darkModeOption: Int = -100,
    val list: MutableList<ComplaintModel> = mutableListOf()
)