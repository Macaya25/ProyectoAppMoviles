package com.example.proyectoappmoviles.database

import androidx.room.*
import com.example.proyectoappmoviles.ObjectItems.ExampleItem

@Dao
interface RoomDao {
    @Query("SELECT * FROM roomsTable ORDER BY id ASC")
    fun getAllRooms(): List<RoomEntity>

    @Query("DELETE FROM roomsTable")
    fun deleteAllRooms()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRoom(contact: RoomEntity)

    @Delete
    fun deleteRoom(room: RoomEntity)
}