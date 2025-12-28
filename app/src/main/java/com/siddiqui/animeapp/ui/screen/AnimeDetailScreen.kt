package com.siddiqui.animeapp.ui.screen

import android.content.Intent
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.siddiqui.animeapp.data.db.AnimeDetailEntity
import com.siddiqui.animeapp.data.model.AnimeDetailDto
import com.siddiqui.animeapp.data.model.GenreDto
import com.siddiqui.animeapp.data.model.ImageUrlDto
import com.siddiqui.animeapp.data.model.ImagesDto
import com.siddiqui.animeapp.data.model.TrailerDto
import com.siddiqui.animeapp.ui.viewModel.AnimeViewModel
import com.siddiqui.animeapp.util.UiState

@Composable
fun AnimeDetailScreen(
    animeId: Int,
    viewModel: AnimeViewModel,
    navController: NavController
) {

    LaunchedEffect(animeId) {
        viewModel.fetchAnimeDetail(animeId)
    }

    val state by viewModel.animeDetail.collectAsState()

    when (val currentState = state) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val anime =
                currentState.data
            AnimeDetailContent(anime,
                navController)
        }

        is UiState.Error -> {
            SafeAnimeDetailContent(
                anime = null,
                navController = navController
            )
        }
    }
}
@Composable
fun SafeAnimeDetailContent(
    anime: AnimeDetailEntity?,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize().windowInsetsPadding(WindowInsets.safeContent)
            .padding(16.dp)
    ) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = anime?.title ?: "Anime details unavailable",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = anime?.synopsis ?: "This content is not available offline yet.",
            color = Color.Gray
        )
    }
}


@Composable
fun AnimeDetailContent(
    anime: AnimeDetailEntity,
    navController: NavController
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeContent)
    ) {

        item {
            Box {

                TrailerSection(anime)

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        }

        item {
            Text(
                text = anime.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoChip("⭐ ${anime.score ?: "N/A"}")
                InfoChip("🎬 ${anime.episodes ?: "N/A"} Episodes")
            }
        }

        if (anime.genres.isNotEmpty()) {
            item {
                SectionTitle("Genres")
                FlowRow(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    anime.genres.split(",").forEach {
                        InfoChip(it.trim())
                    }
                }
            }
        }

        item {
            SectionTitle("Synopsis")
            Text(
                text = anime.synopsis ?: "No synopsis available",
                modifier = Modifier.padding(horizontal = 12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}


@Composable
fun InfoChip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(12.dp)
    )
}
@Composable
fun TrailerSection(anime: AnimeDetailEntity) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {

        AsyncImage(
            model = anime.posterUrl,
            contentDescription = "Anime Poster",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (!anime.trailerUrl.isNullOrEmpty()) {
            IconButton(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(anime.trailerUrl)
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        Color.Black.copy(alpha = 0.6f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Trailer",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowPreview(){


}