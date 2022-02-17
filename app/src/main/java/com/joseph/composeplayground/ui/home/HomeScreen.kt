package com.joseph.composeplayground.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joseph.composeplayground.ui.home.dto.HomeAction
import com.joseph.composeplayground.ui.theme.ComposePlaygroundTheme
import com.joseph.composeplayground.ui.theme.Suit

// string 리소스로 전부 빼기

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Title()
            Spacer(modifier = Modifier.height(16.dp))
            MovieSearch(title = uiState.value.movieSearch, viewModel = viewModel)
        }
    }
}

@Composable
private fun Title() {
    // 패딩 값 예쁘게 수정
    Column(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Text(
            text = "Compose Playground",
            fontSize = 16.sp,
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Jetpack Compose + MVI",
            fontSize = 10.sp,
            fontFamily = Suit,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
    }
}

@Composable
private fun MovieSearch(title: String, viewModel: HomeViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // border rounding 처리
        TextField(
            value = title,
            onValueChange = { viewModel.movieSearch(it) },
            placeholder = { Text(text = "Search movie") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Movie")
            }
        )
    }
}

@Composable
private fun UpcomingMovies() {

}

@Composable
private fun UpcomingMove() {
}

@Composable
private fun PopularMovies() {
}


@Preview()
@Composable
fun forPreview() {
//    MovieSearch()
}