package com.example.proyectoappmoviles.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
    suspend fun getDecks(
    ): Response<DeckObject>
}