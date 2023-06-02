package com.example.pop_flake

import com.example.pop_flake.data.local.ComingSoonDao
import com.example.pop_flake.data.local.InTheaterDao
import com.example.pop_flake.data.local.MovieDatabase
import com.example.pop_flake.data.local.TopMoviesDao
import com.example.pop_flake.data.pojo.BoxOfficeResponse
import com.example.pop_flake.data.pojo.ComingSoonMovie
import com.example.pop_flake.data.pojo.ComingSoonResponse
import com.example.pop_flake.data.pojo.InTheaterResponse
import com.example.pop_flake.data.pojo.SearchResponse
import com.example.pop_flake.data.pojo.TopMoviesResponse
import com.example.pop_flake.data.remote.ApiService
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.Repo
import com.example.pop_flake.data.repository.RepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
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
class RepoImplTest : BaseTestClassWithRules() {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var database: MovieDatabase

    private lateinit var repoImpl: Repo

    @Mock
    private lateinit var comingSoonDao: ComingSoonDao
    @Mock
    private lateinit var inTheaterDao: InTheaterDao
    @Mock
    private lateinit var topMoviesDao: TopMoviesDao

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repoImpl = RepoImpl(apiService, database)
    }

    @Test
    fun `getComingSoonMovies should return success state when API response is successful`() = runBlocking  {
        // Mock the API response
        var comingSoonResponse = ComingSoonResponse("",listOf())
        `when`(apiService.getComingSoonMovies(anyString())).thenReturn(Response.success(comingSoonResponse))
        // Mock the database and DAO
        `when`(database.comingSoonMovieDao()).thenReturn(comingSoonDao)
        // Invoke the function under test
        val result = repoImpl.getComingSoonMovies("token")
        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<ComingSoonResponse>>()
        result.collect { states.add(it) }
        assertEquals(ResponseState.Loading, states[0])
        assertEquals(ResponseState.Success(comingSoonResponse), states[1])
        // Verify that the insertMovies method was called with the correct data
        verify(comingSoonDao).insertMovies(comingSoonResponse.items)
    }
    @Test
    fun `getComingSoonMovies should return success state when API response is failure`() = runBlocking  {
        // Mock the API response
        var comingSoonResponse = ComingSoonResponse("error",listOf())
        `when`(apiService.getComingSoonMovies(anyString())).thenReturn(Response.success(comingSoonResponse))
        `when`(database.comingSoonMovieDao()).thenReturn(comingSoonDao)

        // Invoke the function under test
        val result = repoImpl.getComingSoonMovies("token")
        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<ComingSoonResponse>>()
        result.collect { states.add(it) }
        assertEquals(ResponseState.Loading, states[0])
        assertEquals(ResponseState.Success(comingSoonResponse), states[1])
    }
    @Test
    fun `getTheaterMovies should return success state when API response is successful`() = runBlocking  {
        // Mock the API response
        var theaterResponse = InTheaterResponse("",listOf())
        `when`(apiService.getInTheatersMovies(anyString())).thenReturn(Response.success(theaterResponse))
        // Mock the database and DAO
        `when`(database.inTheaterMovieDao()).thenReturn(inTheaterDao)
        // Invoke the function under test
        val result = repoImpl.getInTheatersMovies("token")
        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<InTheaterResponse>>()
        result.collect { states.add(it) }
        assertEquals(ResponseState.Loading, states[0])
        assertEquals(ResponseState.Success(theaterResponse), states[1])
        // Verify that the insertMovies method was called with the correct data
        verify(inTheaterDao).insertMovies(theaterResponse.items)
    }
    @Test
    fun `getTopRaredMovies should return success state when API response is successful`() = runBlocking  {
        // Mock the API response
        var topRatedResponse = TopMoviesResponse("",listOf())
        `when`(apiService.getTopRatedMovies(anyString())).thenReturn(Response.success(topRatedResponse))
        // Mock the database and DAO
        `when`(database.topMoviesDao()).thenReturn(topMoviesDao)
        // Invoke the function under test
        val result = repoImpl.getTopRatedMovies("token")
        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<TopMoviesResponse>>()
        result.collect { states.add(it) }
        assertEquals(ResponseState.Loading, states[0])
        assertEquals(ResponseState.Success(topRatedResponse), states[1])
        // Verify that the insertMovies method was called with the correct data
        verify(topMoviesDao).insertMovies(topRatedResponse.items)
    }
    @Test
    fun `getBoxMovies should return success state when API response is successful`() = runBlocking  {
        // Mock the API response
        var boxOfficeResponse = BoxOfficeResponse("",listOf())
        `when`(apiService.getBoxOfficeMovies(anyString())).thenReturn(Response.success(boxOfficeResponse))
        // Mock the database and DAO
        // Invoke the function under test
        val result = repoImpl.getBoxOfficeMovies("token")
        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<BoxOfficeResponse>>()
        result.collect { states.add(it) }
        assertEquals(ResponseState.Loading, states[0])
        assertEquals(ResponseState.Success(boxOfficeResponse), states[1])
    }
    @Test
    fun `getSearchMovies should return success state when API response is successful`() = runBlocking  {
        // Mock the API response
        var searchResponse = SearchResponse("","",listOf(),"")
        `when`(apiService.getSearchMovies(anyString(), anyString())).thenReturn(Response.success(searchResponse))
        // Mock the database and DAO
        // Invoke the function under test
        val result = repoImpl.searchMovies("kkk","token")
        // Collect the flow and verify the emitted states
        val states = mutableListOf<ResponseState<SearchResponse>>()
        result.collect { states.add(it) }
        assertEquals(ResponseState.Loading, states[0])
        assertEquals(ResponseState.Success(searchResponse), states[1])
    }
}



