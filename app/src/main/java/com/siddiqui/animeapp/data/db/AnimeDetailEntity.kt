package com.siddiqui.animeapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_detail")
data class AnimeDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val posterUrl: String,
    val trailerUrl: String?,
    val genres: String
)

