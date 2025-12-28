package com.siddiqui.animeapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDetailDao {

    @Query("SELECT * FROM anime_detail WHERE id = :animeId")
    suspend fun getAnimeDetail(animeId: Int): AnimeDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeDetail(detail: AnimeDetailEntity)
}
