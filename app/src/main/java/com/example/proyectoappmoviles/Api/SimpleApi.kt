package com.example.proyectoappmoviles.Api

import com.example.proyectoappmoviles.ObjectItems.Deck
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
}