package com.siddiqui.animeapp.data.api

object NetworkModule {
    fun provideAnimeApi(): AnimeApiService =
        RetrofitClient.retrofit.create(AnimeApiService::class.java)
}
