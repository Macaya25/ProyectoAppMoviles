package com.example.proyectoappmoviles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectoappmoviles.ObjectItems.ExampleItem

@Dao
interface RoomDao {
    @Query("SELECT * FROM roomsTable ORDER BY id ASC")
    fun getAllRooms(): List<RoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRoom(contact: RoomEntity)

    @Query("DELETE FROM roomsTable WHERE id = :roomId")
    fun deleteRoom(roomId: Int)
}