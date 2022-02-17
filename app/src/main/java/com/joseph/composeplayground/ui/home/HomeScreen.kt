package com.joseph.composeplayground.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.joseph.composeplayground.R
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.ui.home.dto.HomeAction
import com.joseph.composeplayground.ui.theme.ComposePlaygroundTheme
import com.joseph.composeplayground.ui.theme.Suit

// string 리소스로 전부 빼기
// 페이징
// 라운딩 수정

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
            modifier = Modifier
                .padding(16.dp)
        ) {
            Title()
            Spacer(modifier = Modifier.height(16.dp))
            MovieSearch(title = uiState.value.movieSearch, viewModel = viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            UpcomingMovies(movies = uiState.value.upComingMovieList.movies)
            Spacer(modifier = Modifier.height(16.dp))
            PopularMovies(movies = uiState.value.popularMovieList.movies)

        }
    }
}

@Composable
private fun Title() {
    // 패딩 값 예쁘게 수정
    Column {
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
            .height(34.dp)
            .clip(shape = RoundedCornerShape(10.dp)),
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
private fun UpcomingMovies(movies: List<Movie>) {
    Column() {
        Text(
            text = "Upcoming Movies",
            fontSize = 14.sp,
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow() {
            items(items = movies) { movie ->
                UpcomingMovie(movie = movie)
            }
        }
    }
}

@Composable
private fun UpcomingMovie(movie: Movie) {
    Box(
        modifier = Modifier
            .size(180.dp, 260.dp)
            .clip(RoundedCornerShape(5.dp))
            .padding(horizontal = 4.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.8f)
                    ),
                    0f,
                    100f,
                )
            )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            ),
            contentDescription = movie.title,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 4.dp, end = 4.dp)
                .wrapContentWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = movie.title ?: "",
                fontSize = 16.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End
            )
            Text(
                text = movie.originalTitle ?: "",
                fontSize = 12.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Light,
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun PopularMovies(movies: List<Movie>) {
    Column() {
        Text(
            text = "Popular Movies",
            fontSize = 14.sp,
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn() {
            item { Spacer(modifier = Modifier.width(0.dp)) }
            items(items = movies) { movie ->
                PopularMovie(movie = movie)
            }
        }
    }
}

@Composable
private fun PopularMovie(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(5.dp))
            .padding(vertical = 4.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.8f)
                    ),
                    0f,
                    100f,
                )
            )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            ),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(4.dp)
                .wrapContentWidth()
        ) {
            Column(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = Color.White)
                    .clip(shape = RoundedCornerShape(5.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = Color.Black,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "4.8",
                    fontSize = 8.sp,
                    fontFamily = Suit,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = Color.White)
                    .clip(shape = RoundedCornerShape(5.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 8.sp,
                    text = if (movie.adult == true) "성인" else "전체",
                    color = if (movie.adult == true) Color.Red else Color.Black
                )
            }

        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
                .wrapContentWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = movie.title ?: "",
                fontSize = 12.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End
            )
            Text(
                text = movie.originalTitle ?: "",
                fontSize = 12.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Light,
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
@Preview
fun HI() {
    Column(
        modifier = Modifier
            .size(32.dp)
            .background(color = Color.White)
            .clip(shape = RoundedCornerShape(5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star",
            tint = Color.Black,
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "4.8",
            fontSize = 8.sp,
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}