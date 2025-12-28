package com.siddiqui.animeapp.data.model

data class AnimeDetailResponse(val data: AnimeDetailDto)

data class AnimeDetailDto(
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<GenreDto>,
    val images: ImagesDto,
    val trailer: TrailerDto?,
)

data class GenreDto(val name: String)
data class TrailerDto(val youtube_id: String?,
                      val embed_url:String?)

