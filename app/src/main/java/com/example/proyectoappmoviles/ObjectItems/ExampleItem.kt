package com.example.proyectoappmoviles.ObjectItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExampleItem(val roomId:String, val roomName:String, val waitingDelete: Boolean,
                       val offlinePassword: String?, val deck: Deck) : Parcelable