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

    @GET("rooms/{roomName}")
    suspend fun getRoom(
            @Header("token") token:String,
            @Path ("roomName") roomName : String
    ): Response<GetRoomItem>

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

    @POST("joinRoom")
    suspend fun joinRoom(
            @Header("token") token:String,
            @Body room:LobbyItem
    ): Response<LobbyItem>

    @GET("getResult/{roomName}")
    suspend fun getResult(
            @Header("token") token:String,
            @Path ("roomName") roomName: String
    ): Response<ResultItem>

    @POST("vote")
    suspend fun vote(
            @Header("token") token:String,
            @Body voteItem: VoteItem
    ): Response<VoteItem>

    @POST("reportLocation")
    suspend fun reportLocation(
        @Header("token") token:String,
        @Body locationItem: LocationItem
    ): Response<LocationItem>

}