package com.example.pop_flake.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pop_flake.data.pojo.MovieResult
import com.example.pop_flake.data.pojo.TopRatedMovie
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.data.repository.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: Repo, @Named("auth_token") private val token: String) : ViewModel() {


    private val _searchMoviesLiveData = MutableLiveData<ResponseState<List<MovieResult>>>()
    val searchMoviesLiveData: LiveData<ResponseState<List<MovieResult>>>
        get() = _searchMoviesLiveData


    fun getSearchMovies(query: String) {
        viewModelScope.launch {
            _searchMoviesLiveData.value = ResponseState.Loading
            repo.searchMovies(query,token)
                .onStart { _searchMoviesLiveData.value = ResponseState.Loading }
                .catch { error ->
                    _searchMoviesLiveData.value = error.message?.let { ResponseState.Error(it)
                    }
                }
                .collect { result ->
                    if (result is ResponseState.Success) {
                        if (result.data.results.isEmpty()){
                            _searchMoviesLiveData.value = ResponseState.Error(result.data.errorMessage)
                        }else{
                            _searchMoviesLiveData.value = ResponseState.Success(result.data.results)
                        }
                    } else if (result is ResponseState.Error) {
                        _searchMoviesLiveData.value = ResponseState.Error(result.message)
                    }
                }
        }
    }

}