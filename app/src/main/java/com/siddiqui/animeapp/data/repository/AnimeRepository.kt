package com.siddiqui.animeapp.data.repository

import android.util.Log
import com.siddiqui.animeapp.data.api.AnimeApiService
import com.siddiqui.animeapp.data.db.AnimeDao
import com.siddiqui.animeapp.data.db.AnimeDetailDao
import com.siddiqui.animeapp.data.db.AnimeDetailEntity
import com.siddiqui.animeapp.data.db.AnimeEntity
import com.siddiqui.animeapp.util.toEntity

class AnimeRepository(
    private val api: AnimeApiService,
    private val dao: AnimeDao,
    private val animeDetailDao: AnimeDetailDao
) {

    suspend fun getAnimeList(): List<AnimeEntity> {
        return try {
            val response = api.getTopAnime()
            val list = response.data.map {
                AnimeEntity(
                    it.mal_id,
                    it.title,
                    it.episodes,
                    it.score,
                    it.images.jpg.image_url,
                )
            }
            dao.insertAll(list)
            list
        } catch (e: Exception) {
            dao.getAll()
        }
    }

    suspend fun getAnimeDetail(animeId: Int): AnimeDetailEntity {

        val local = animeDetailDao.getAnimeDetail(animeId)
        if (local != null) {
            Log.d("TAG", "Loaded from DB")
            return local
        }

        val dto = api.getAnimeDetail(animeId).data
        val entity = dto.toEntity(animeId)

        animeDetailDao.insertAnimeDetail(entity)
        Log.d("TAG", "Loaded from API and cached")

        return entity
    }


}
