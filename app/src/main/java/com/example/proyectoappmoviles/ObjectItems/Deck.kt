package com.example.proyectoappmoviles.ObjectItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deck (
        val name: String,
        val cards: List<String>
) : Parcelable