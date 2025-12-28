package com.siddiqui.animeapp.data.model

data class TopAnimeResponse(val data: List<AnimeDto>)

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: ImagesDto,

)

data class ImagesDto(val jpg: ImageUrlDto)
data class ImageUrlDto(val image_url: String,val large_image_url: String)

