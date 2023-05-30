package com.example.pop_flake

import com.example.pop_flake.data.local.ComingSoonDao
import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.pojo.ComingSoonMovie
import com.example.pop_flake.data.pojo.ComingSoonResponse
import com.example.pop_flake.data.remote.ApiService
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.Repo
import com.example.pop_flake.data.repository.RepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class RepoImplTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var database: MovieDatabase

    private lateinit var repoImpl: Repo

    @Mock
    private lateinit var comingSoonDao: ComingSoonDao



    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repoImpl = RepoImpl(apiService, database)
    }

    @Test
    fun `getComingSoonMovies should return success state when API response is successful`() = runBlockingTest {
        // Mock the API response

        var comingSoonResponse = ComingSoonResponse("",listOf<ComingSoonMovie>())

        `when`(apiService.getComingSoonMovies(anyString())).thenReturn(Response.success(comingSoonResponse))

        // Mock the database and DAO

        `when`(database.comingSoonMovieDao()).thenReturn(comingSoonDao)

        // Invoke the function under test
        val result = repoImpl.getComingSoonMovies("token")

        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<ComingSoonResponse>>()
        result.collect { states.add(it) }

        assertEquals(ResponseState.Loading, states[0]) // Verify initial loading state
        assertEquals(ResponseState.Success(comingSoonResponse), states[1]) // Verify success state

        // Verify that the insertMovies method was called with the correct data
        verify(comingSoonDao).insertMovies(comingSoonResponse.items)
    }
}



