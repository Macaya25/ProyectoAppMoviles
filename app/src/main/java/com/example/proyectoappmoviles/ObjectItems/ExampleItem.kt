package com.example.proyectoappmoviles.ObjectItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExampleItem (val name: String, val password: String, val deck: String) : Parcelable