package com.example.proyectoappmoviles.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "deckTable")
@Parcelize
data class DeckEntity (
    @PrimaryKey
    @ColumnInfo(name = "deckName") val name: String,
    @ColumnInfo(name = "cards") val cards: String
): Parcelable