package com.example.proyectoappmoviles.Api

import com.example.proyectoappmoviles.ObjectItems.Deck
import com.example.proyectoappmoviles.ObjectItems.LobbiesListItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {
    @POST("login")
    suspend fun getLogin(
        @Body user: UserObject
    ):Response<UserObject>

    @POST("signup")
    suspend fun getSignUp(
        @Body user:UserObject
    ):Response<UserObject>

    @GET("decks")
    fun getDecks(
    ): Call<List<Deck>>

    @GET("rooms")
    suspend fun getRooms(
        @Header("token") token:String
    ): LobbiesListItem
}