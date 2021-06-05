package com.example.proyectoappmoviles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DeckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: DeckEntity)
}