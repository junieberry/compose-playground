package com.joseph.composeplayground.ui.home.dto

import com.joseph.composeplayground.base.mvi.dto.UiAction
import com.joseph.composeplayground.model.Movie


sealed class HomeAction : UiAction {
    class FetchUpComingMovieList(val fetchedList: List<Movie>) : HomeAction()
    class FetchPopularMovieList(val fetchedList: List<Movie>) : HomeAction()
}