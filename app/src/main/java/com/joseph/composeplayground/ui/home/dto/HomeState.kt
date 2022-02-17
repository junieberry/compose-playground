package com.joseph.composeplayground.ui.home.dto

import com.joseph.composeplayground.base.mvi.dto.UiState
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.util.LoadState


data class HomeState(
    val loadState: LoadState,
    val upComingMovieList: UpComingMovieState,
    val movieSearch: String,
) : UiState {

    companion object {
        fun getInitial(): HomeState {
            return HomeState(
                loadState = LoadState.Idle,
                upComingMovieList = UpComingMovieState.getInitial(),
                movieSearch = ""
            )
        }
    }

    data class UpComingMovieState(
        val movies: List<Movie>,
        val page: Int
    ) {
        companion object {
            fun getInitial(): UpComingMovieState {
                return UpComingMovieState(
                    movies = emptyList(),
                    page = 1
                )
            }
        }
    }

}