package com.example.proyectoappmoviles.database

import android.os.Parcelable
import androidx.room.*
import com.example.proyectoappmoviles.ObjectItems.Deck
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "roomsTable")
@Parcelize
data class RoomEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "room_id") val room_id: String,
        @ColumnInfo(name = "roomName") val name: String,
        @Embedded val deck: DeckEntity
): Parcelable
