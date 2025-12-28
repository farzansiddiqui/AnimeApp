package com.siddiqui.animeapp.data.api

import com.siddiqui.animeapp.data.model.AnimeDetailResponse
import com.siddiqui.animeapp.data.model.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApiService {

    @GET("v4/top/anime")
    suspend fun getTopAnime(): TopAnimeResponse

    @GET("v4/anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): AnimeDetailResponse
}
