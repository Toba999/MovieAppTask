package com.example.pop_flake.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pop_flake.data.local.ComingSoonDao
import com.example.pop_flake.data.local.HelperSharedPreferences
import com.example.pop_flake.data.local.InTheaterDao
import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.local.TopMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideHelperSharedPreferences(@ApplicationContext context: Context): HelperSharedPreferences {
        return HelperSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(
            application,
            MovieDatabase::class.java,
            "app_database"
        ).build()
    }

}