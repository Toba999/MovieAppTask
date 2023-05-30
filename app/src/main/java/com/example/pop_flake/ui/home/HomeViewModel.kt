package com.example.pop_flake.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pop_flake.data.local.ComingSoonDao
import com.example.pop_flake.data.local.InTheaterDao
import com.example.pop_flake.data.local.TopMoviesDao
import com.example.pop_flake.data.pojo.BoxOfficeMovie
import com.example.pop_flake.data.pojo.ComingSoonMovie
import com.example.pop_flake.data.pojo.InTheaterMovie
import com.example.pop_flake.data.pojo.TopRatedMovie
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.Repo
import com.example.pop_flake.ui.home.comingSoonAdapter.ComingSoonPagingSource
import com.example.pop_flake.ui.home.inTheaterAdapter.InTheaterPagingSource
import com.example.pop_flake.ui.home.topMoviesAdapter.TopMoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: Repo,
    @Named("auth_token") private val token: String,
    @Named("TopMoviePaging") private val topMoviePagingSource: TopMoviePagingSource,
    @Named("ComingMoviePaging") private val comingMoviePagingSource: ComingSoonPagingSource,
    @Named("TheaterMoviePaging") private val theaterMoviePagingSource: InTheaterPagingSource
    )
    : ViewModel() {


    private val pageSize = 20

    private val _topRatedMoviesLiveData = MutableLiveData<ResponseState<List<TopRatedMovie>>>()
    val topRatedMoviesLiveData: LiveData<ResponseState<List<TopRatedMovie>>>
        get() = _topRatedMoviesLiveData
    private val _comingSoonMoviesLiveData = MutableLiveData<ResponseState<List<ComingSoonMovie>>>()
    val comingSoonMoviesLiveData: LiveData<ResponseState<List<ComingSoonMovie>>>
        get() = _comingSoonMoviesLiveData
    private val _inTheaterMoviesLiveData = MutableLiveData<ResponseState<List<InTheaterMovie>>>()
    val inTheaterMoviesLiveData: LiveData<ResponseState<List<InTheaterMovie>>>
        get() = _inTheaterMoviesLiveData
    private val _boxOfficeMoviesLiveData = MutableLiveData<ResponseState<List<BoxOfficeMovie>>>()
    val boxOfficeMoviesLiveData: LiveData<ResponseState<List<BoxOfficeMovie>>>
        get() = _boxOfficeMoviesLiveData




    val topMoviesFlow: Flow<PagingData<TopRatedMovie>> = Pager(PagingConfig(pageSize)) {
        topMoviePagingSource
    }.flow.cachedIn(viewModelScope)

    val comingSoonMoviesFlow: Flow<PagingData<ComingSoonMovie>> = Pager(PagingConfig(pageSize)) {
        comingMoviePagingSource
    }.flow.cachedIn(viewModelScope)

    val inTheaterMoviesFlow: Flow<PagingData<InTheaterMovie>> = Pager(PagingConfig(pageSize)) {
        theaterMoviePagingSource
    }.flow.cachedIn(viewModelScope)

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMoviesLiveData.value = ResponseState.Loading
            repo.getTopRatedMovies(token)
                .onStart { _topRatedMoviesLiveData.value = ResponseState.Loading }
                .catch { error -> _topRatedMoviesLiveData.value =
                    error.message?.let { ResponseState.Error(it) }
                }
                .collect { result ->
                    if (result is ResponseState.Success) {
                        if (result.data.items.isEmpty()){
                            _topRatedMoviesLiveData.value = ResponseState.Error(result.data.errorMessage)
                        }else{
                            _topRatedMoviesLiveData.value = ResponseState.Success(result.data.items)
                        }
                    } else if (result is ResponseState.Error) {
                        _topRatedMoviesLiveData.value = ResponseState.Error(result.message)
                    }
                }
        }
    }

    fun getComingSoonMovies() {
        viewModelScope.launch {
            _comingSoonMoviesLiveData.value = ResponseState.Loading
            repo.getComingSoonMovies(token)
                .onStart { _comingSoonMoviesLiveData.value = ResponseState.Loading }
                .catch { error -> _comingSoonMoviesLiveData.value =
                    error.message?.let { ResponseState.Error(it) }
                }
                .collect { result ->
                    if (result is ResponseState.Success) {
                        if (result.data.items.isEmpty()){
                            _comingSoonMoviesLiveData.value = ResponseState.Error(result.data.errorMessage)
                        }else{
                            _comingSoonMoviesLiveData.value = ResponseState.Success(result.data.items)
                        }
                    } else if (result is ResponseState.Error) {
                        _comingSoonMoviesLiveData.value = ResponseState.Error(result.message)
                    }
                }
        }
    }

    fun getInTheaterMovies() {
        viewModelScope.launch {
            _inTheaterMoviesLiveData.value = ResponseState.Loading
            repo.getInTheatersMovies(token)
                .onStart { _inTheaterMoviesLiveData.value = ResponseState.Loading }
                .catch { error -> _inTheaterMoviesLiveData.value =
                    error.message?.let { ResponseState.Error(it) }
                }
                .collect { result ->
                    if (result is ResponseState.Success) {
                        if (result.data.items.isEmpty()){
                            _inTheaterMoviesLiveData.value = ResponseState.Error(result.data.errorMessage)
                        }else{
                            _inTheaterMoviesLiveData.value = ResponseState.Success(result.data.items)
                        }
                    } else if (result is ResponseState.Error) {
                        _inTheaterMoviesLiveData.value = ResponseState.Error(result.message)
                    }
                }
        }
    }

    fun getBoxOfficeMovies() {
        viewModelScope.launch {
            _boxOfficeMoviesLiveData.value = ResponseState.Loading
            repo.getBoxOfficeMovies(token)
                .onStart { _boxOfficeMoviesLiveData.value = ResponseState.Loading }
                .catch { error ->
                    _boxOfficeMoviesLiveData.value = error.message?.let { ResponseState.Error(it)
                    }
                }
                .collect { result ->
                    if (result is ResponseState.Success) {
                        if (result.data.items.isEmpty()){
                            _boxOfficeMoviesLiveData.value = ResponseState.Error(result.data.errorMessage)
                        }else{
                            _boxOfficeMoviesLiveData.value = ResponseState.Success(result.data.items)
                        }
                    } else if (result is ResponseState.Error) {
                        _boxOfficeMoviesLiveData.value = ResponseState.Error(result.message)
                    }
                }
        }
    }

}