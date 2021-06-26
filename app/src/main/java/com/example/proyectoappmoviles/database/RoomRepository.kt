package com.example.proyectoappmoviles.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class RoomRepository(private val database :RoomsDatabase) {

    fun getRoomDao(): RoomDao{
        return database.roomDao()
    }

    fun getDeckDao(): DeckDao{
        return database.deckDao()
    }
}