package com.example.proyectoappmoviles.ObjectItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deck (
    val deckName: String,
    val deck: String
) : Parcelable