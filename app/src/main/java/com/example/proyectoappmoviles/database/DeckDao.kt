package com.example.proyectoappmoviles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeckDao {
    @Query("SELECT * FROM deckTable")
    fun getAllDecks(): List<DeckEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: DeckEntity)
}