package com.example.proyectoappmoviles.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyectoappmoviles.ObjectItems.Deck

@Entity(tableName = "roomsTable")
data class RoomEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "deck") val deck: String
)
