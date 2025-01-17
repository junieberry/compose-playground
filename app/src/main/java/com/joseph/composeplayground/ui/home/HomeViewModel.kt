package com.joseph.composeplayground.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.joseph.composeplayground.base.BaseViewModel
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.ui.home.dto.HomeAction
import com.joseph.composeplayground.ui.home.dto.HomeEvent
import com.joseph.composeplayground.ui.home.dto.HomeState
import com.joseph.composeplayground.util.LoadState
import com.joseph.domain.usecases.FetchPopularMovieListUseCase
import com.joseph.domain.usecases.FetchUpComingMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUpComingMovieListUseCase: FetchUpComingMovieListUseCase,
    private val fetchPopularMovieListUserCase: FetchPopularMovieListUseCase,
) : BaseViewModel<HomeState, HomeAction, HomeEvent>(HomeState.getInitial()) {

    init {
        viewModelScope.launch {
            fetchUpComingMovieList(uiState.value)
            fetchPopularMovieList(uiState.value)
        }
    }

    fun movieSearch(movieSearch: String) {
        val newState = uiState.value.copy(movieSearch = movieSearch)
        updateState(newState)
    }

    override suspend fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.FetchUpComingMovieList -> {
                fetchUpComingMovieList(uiState.value)
            }
            is HomeAction.FetchPopularMovieList -> {
                fetchPopularMovieList(uiState.value)
            }
        }
    }

    private suspend fun fetchUpComingMovieList(state: HomeState) {
        val params = FetchUpComingMovieListUseCase.Params(page = state.upComingMovieList.page)
        fetchUpComingMovieListUseCase.invoke(params = params)
            .collectWithCallback(
                onSuccess = { movieListEntity ->
                    val newState = uiState.value.copy(
                        loadState = LoadState.Idle,
                        upComingMovieList = uiState.value.upComingMovieList.copy(
                            movies = uiState.value.upComingMovieList.movies + movieListEntity.results.map {
                                Movie.fromEntity(
                                    it
                                )
                            },
                            page = movieListEntity.page + 1,
                        )
                    )
                    // state를 새로 할당하는건 state가 바뀌어야 뷰가 다시 그려져서?
                    // live data 느낌이랑 다른건가
                    updateState(newState)
                },

                onFailed = { message ->
                    val newState = uiState.value.copy(loadState = LoadState.Failed)
                    updateState(newState)
                }
            )
    }

    private suspend fun fetchPopularMovieList(state: HomeState) {
        val params = FetchPopularMovieListUseCase.Params(page = state.popularMovieList.page)
        fetchPopularMovieListUserCase.invoke(params = params)
            .collectWithCallback(
                onSuccess = { movieListEntity ->
                    val newState = uiState.value.copy(
                        loadState = LoadState.Idle,
                        popularMovieList = uiState.value.popularMovieList.copy(
                            movies = uiState.value.popularMovieList.movies + movieListEntity.results.map {
                                Movie.fromEntity(
                                    it
                                )
                            },
                            page = movieListEntity.page + 1,
                        )
                    )
                    Log.d("로그", "onsuccess ${movieListEntity}")
                    updateState(newState)
                },

                onFailed = { message ->
                    Log.d("로그", "onfailed 으악")
                    val newState = uiState.value.copy(loadState = LoadState.Failed)
                    updateState(newState)
                }
            )

    }
}