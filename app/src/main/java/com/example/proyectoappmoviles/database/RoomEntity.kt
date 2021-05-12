package com.example.proyectoappmoviles.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roomsTable")
data class RoomEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "frontendTeam") val frontendTeam: String
)
