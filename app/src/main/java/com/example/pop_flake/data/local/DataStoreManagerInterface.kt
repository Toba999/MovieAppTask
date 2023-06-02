package com.example.pop_flake.data.local

import kotlinx.coroutines.flow.Flow

interface DataStoreManagerInterface {

    suspend fun setInt(key:String,value:Int)

    fun getInt(key: String, defaultValue:Int): Flow<Int>

    suspend fun setString(key:String,value:String)

    fun getString(key:String,defaultValue:String): Flow<String>
}