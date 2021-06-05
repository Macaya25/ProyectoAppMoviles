package com.example.proyectoappmoviles.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room

class RoomRepository(application: Application) {

    private val database = Room.databaseBuilder(application, RoomsDatabase::class.java, "roomsDb")
        .build()

    fun getRoomDao(): RoomDao{
        return database.roomDao()
    }

    fun getDeckDao(): DeckDao{
        return database.deckDao()
    }
}