package com.siddiqui.animeapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.siddiqui.animeapp.data.db.AnimeEntity
import com.siddiqui.animeapp.ui.viewModel.AnimeViewModel
import com.siddiqui.animeapp.util.UiState

@Composable
fun AnimeListScreen(viewModel: AnimeViewModel, nav: NavController) {

    val state by viewModel.animeList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAnimeList()
    }
    when (val currentState = state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            LazyColumn(
                contentPadding = WindowInsets.systemBars.asPaddingValues()
            ) {
                items(currentState.data) { anime ->
                    AnimeItem(anime) {
                        nav.navigate("detail/${anime.id}")
                    }
                }
            }
        }

        is UiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: Failed to load list")
            }
        }
    }
}



@Composable
fun AnimeItem(
    anime: AnimeEntity,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(100.dp)

            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(anime.title, fontWeight = FontWeight.Bold)
                Text("Episodes: ${anime.episodes ?: "N/A"}")
                Text("Rating: ${anime.rating ?: "N/A"}")
            }
        }
    }
}



