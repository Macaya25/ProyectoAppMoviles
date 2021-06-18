package com.example.proyectoappmoviles.database

import androidx.room.*
import com.example.proyectoappmoviles.ObjectItems.ExampleItem

@Dao
interface RoomDao {
    @Query("SELECT * FROM roomsTable")
    fun getAllRooms(): List<RoomEntity>

    @Query("DELETE FROM roomsTable")
    fun deleteAllRooms()

    @Query("UPDATE roomsTable SET waitingDelete = 'True' WHERE room_id = :id")
    fun setToDelete(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRoom(contact: RoomEntity)

    @Query("DELETE FROM roomsTable WHERE roomName = :name")
    fun deleteRoom(name: String)
}