package com.example.pop_flake.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class HelperSharedPreferences @Inject constructor(private val context: Context) {

   companion object {
       const val  viewMode = "ViewMode"
       const val dark = "Dark"
       const val light = "Light"
   }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    fun addData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)!!
    }

    fun deleteData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}