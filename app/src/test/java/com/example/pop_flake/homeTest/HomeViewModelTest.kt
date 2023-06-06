package com.example.pop_flake.homeTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pop_flake.data.pojo.TopMoviesResponse
import com.example.pop_flake.data.pojo.TopRatedMovie
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.Repo
import com.example.pop_flake.getOrAwaitValueTest
import com.example.pop_flake.ui.home.HomeViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {


    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    // Use the @MockK annotation to create a mock of the Repo class
    @MockK(relaxed = true)
    lateinit var repo: Repo

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        // Initialize MockK annotations
        MockKAnnotations.init(this)

        Dispatchers.setMain(testDispatcher)

        // Create an instance of HomeViewModel using the mocked Repo dependency
        viewModel = HomeViewModel(repo, "test_token", mockk(), mockk(), mockk())
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()

        // Clear all mock interactions
        unmockkAll()
    }

    @Test
    fun `test getTopRatedMovies success`() = testScope.runBlockingTest {
        val topRatedMovies = listOf(
            TopRatedMovie(),
            TopRatedMovie()
        )
        val successResponseState = ResponseState.Success(TopMoviesResponse("",topRatedMovies))

        coEvery { repo.getTopRatedMovies(any()) } returns flowOf(successResponseState)

        viewModel.getTopRatedMovies()

        val value = viewModel.topRatedMoviesLiveData.getOrAwaitValueTest()

        assertEquals(value, ResponseState.Success(topRatedMovies))

        // Verify that the repo's getTopRatedMovies method was called with the correct token
        coVerify { repo.getTopRatedMovies("test_token") }
    }


    //same for the remain funs

}
