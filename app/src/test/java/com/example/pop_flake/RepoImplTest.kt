package com.example.pop_flake

import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.pojo.TopMoviesResponse
import com.example.pop_flake.data.pojo.TopRatedMovie
import com.example.pop_flake.data.remote.ApiService
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.RepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class RepoImplTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var database: MovieDatabase

    private lateinit var repoImpl: RepoImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repoImpl = RepoImpl(apiService, database)
    }

    @Test
    fun `getTopRatedMovies should return success state when API response is successful`() = runBlocking {
        val token = "token"
        val expectedItems = listOf(TopRatedMovie(/* movie data */))
        val expectedResponse = TopMoviesResponse("", expectedItems)
        `when`(apiService.getTopRatedMovies(token)).thenReturn(Response.success(expectedResponse))
        // Act
        // Act
        val flow = repoImpl.getTopRatedMovies(token)
        val responseState = mutableListOf<ResponseState<TopMoviesResponse>>()
        flow.collect { responseState.add(it) }

        // Assert
        assertEquals(ResponseState.Loading, responseState[0])
        assertEquals(ResponseState.Success(expectedResponse), responseState[1])
    }

    // Write similar unit tests for other functions in RepoImpl

    // ...
}
