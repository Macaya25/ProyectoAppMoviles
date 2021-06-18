package com.example.proyectoappmoviles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeckDao {
    @Query("SELECT * FROM deckTable")
    fun getAllDecks(): List<DeckEntity>

    @Query("SELECT * FROM deckTable WHERE deckName = :deckName LIMIT 1")
    fun getDeck(deckName: String): DeckEntity

    @Query("SELECT * FROM deckTable WHERE deckName = :deckName LIMIT 1")

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: DeckEntity)
}