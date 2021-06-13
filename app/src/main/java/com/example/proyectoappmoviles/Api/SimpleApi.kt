package com.example.proyectoappmoviles.Api

import com.example.proyectoappmoviles.ObjectItems.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.lang.invoke.MethodHandles

interface SimpleApi {
    //User Login
    @POST("login")
    suspend fun getLogin(
        @Body user: UserObject
    ):Response<UserObject>

    @POST("signup")
    suspend fun getSignUp(
        @Body user:UserObject
    ):Response<UserObject>


    //Get Decks
    @GET("decks")
    fun getDecks(
    ): Call<List<Deck>>


    //Room logic
    @GET("rooms")
    suspend fun getRooms(
        @Header("token") token:String
    ): LobbiesListItem

    @GET("room")
    suspend fun getRoom(
            @Header("token") token:String,
            @Query ("roomName ") roomName : String
    ): Response<LobbyItem>

    @POST("rooms")
    suspend fun createRoom(
            @Header("token") token:String,
            @Body room:LobbyItem
    ): Response<LobbyItem>

    @HTTP(method = "DELETE", path = "room", hasBody = true)
    suspend fun deleteRoom(
            @Header("token") token:String,
            @Body room:LobbyItem
    ): Response<LobbyItem>

    @POST("joinRoom")//Error raro de response vacia
    suspend fun joinRoom(
            @Header("token") token:String,
            @Body room:LobbyItem
    ): Response<LobbyItem>

    @GET("getResult")
    suspend fun getResult(
            @Header("token") token:String,
            @Query ("roomName ") room : LobbyItem
    ): Response<ResultItem>

    @POST("vote")
    suspend fun vote(
            @Header("token") token:String,
            @Body voteItem: VoteItem
    ): Response<VoteItem>

}