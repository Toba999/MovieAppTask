package com.example.pop_flake.di

import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.remote.ApiService
import com.example.pop_flake.data.repository.Repo
import com.example.pop_flake.data.repository.RepoImpl
import com.example.pop_flake.ui.home.topMoviesAdapter.TopMoviePagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: ApiService,database: MovieDatabase): Repo {
        return RepoImpl(apiService,database)
    }
}


