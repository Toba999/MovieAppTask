package com.example.pop_flake.searchTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pop_flake.data.pojo.MovieResult
import com.example.pop_flake.data.pojo.SearchResponse
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.Repo
import com.example.pop_flake.getOrAwaitValueTest
import com.example.pop_flake.ui.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
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

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val repo: Repo = mockk()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testGetSearchMovies() = testScope.runBlockingTest {
        val query = "test_query"
        val movieResultList = listOf(
            MovieResult(),
            MovieResult()
        )
        val successResponseState = ResponseState.Success(
            SearchResponse("", "", movieResultList, "")
        )

        coEvery { repo.searchMovies(query, "test_token") } returns flowOf(successResponseState)

        viewModel = SearchViewModel(repo, "test_token")
        viewModel.getSearchMovies(query)

        val value = viewModel.searchMoviesLiveData.getOrAwaitValueTest()

        assertEquals(value, ResponseState.Success(movieResultList))
    }

}
