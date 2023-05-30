package com.example.pop_flake.di

import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.ui.home.comingSoonAdapter.ComingSoonPagingSource
import com.example.pop_flake.ui.home.inTheaterAdapter.InTheaterPagingSource
import com.example.pop_flake.ui.home.topMoviesAdapter.TopMoviePagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @Named("TopMoviePaging")
    fun provideTopMoviePagingSource(database: MovieDatabase): TopMoviePagingSource {
        return TopMoviePagingSource(database)
    }
    @Provides
    @Named("ComingMoviePaging")
    fun provideComingMoviePagingSource(database: MovieDatabase): ComingSoonPagingSource {
        return ComingSoonPagingSource(database)
    }
    @Provides
    @Named("TheaterMoviePaging")
    fun provideTheaterMoviePagingSource(database: MovieDatabase): InTheaterPagingSource {
        return InTheaterPagingSource(database)
    }


}