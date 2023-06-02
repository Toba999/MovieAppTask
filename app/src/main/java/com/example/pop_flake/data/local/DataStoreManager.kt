package com.example.pop_flake.data.local

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
 class DataStoreManager @Inject constructor(@ApplicationContext var context: Context) : DataStoreManagerInterface {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "data_store")

    override suspend fun setInt(key:String, value:Int){
        dataStore.setValue(preferencesKey(key),value)
    }

    override fun getInt(key: String, defaultValue:Int)= dataStore.getValueFlow(preferencesKey(key),defaultValue)

    override suspend fun setString(key:String, value:String){
        dataStore.setValue(preferencesKey(key),value)
    }

    override fun getString(key:String, defaultValue:String)= dataStore.getValueFlow(preferencesKey(key),defaultValue)
}


fun <T> DataStore<Preferences>.getValueFlow(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> {
    return this.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }
}

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}