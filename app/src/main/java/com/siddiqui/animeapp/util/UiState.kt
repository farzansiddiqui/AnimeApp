package com.siddiqui.animeapp.util

import com.siddiqui.animeapp.data.db.AnimeDetailEntity
import com.siddiqui.animeapp.data.model.AnimeDetailDto


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val msg: String) : UiState<Nothing>()
}

fun AnimeDetailDto.toEntity(animeId: Int): AnimeDetailEntity {
    return AnimeDetailEntity(
        id = animeId,
        title = title,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        posterUrl = images.jpg.large_image_url,
        trailerUrl = trailer?.embed_url,
        genres = genres.joinToString { it.name }
    )
}




