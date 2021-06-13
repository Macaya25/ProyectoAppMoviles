package com.example.proyectoappmoviles.ObjectItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExampleItem(val room_id:String, val name:String, val deck:Deck?, val members:List<String>? ) : Parcelable