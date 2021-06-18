package com.example.proyectoappmoviles.database

import android.os.Parcelable
import androidx.room.*
import com.example.proyectoappmoviles.ObjectItems.Deck
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "roomsTable")
@Parcelize
data class RoomEntity(
        @PrimaryKey
        @ColumnInfo(name = "room_id") val room_id: String,
        @ColumnInfo(name = "roomName") val name: String?,
        @ColumnInfo(name= "waitingDelete") val waitingDelete: Boolean,
        @ColumnInfo(name= "madeOffline") val madeOffline: Boolean,
        @Embedded val deck: DeckEntity
): Parcelable
