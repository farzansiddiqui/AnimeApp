package com.siddiqui.animeapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnimeEntity::class, AnimeDetailEntity::class], version = 2)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun animeDetailDao(): AnimeDetailDao
}
