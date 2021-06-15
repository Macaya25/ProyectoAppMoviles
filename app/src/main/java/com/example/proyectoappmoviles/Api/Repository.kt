package com.example.proyectoappmoviles.Api

import com.example.proyectoappmoviles.ObjectItems.*
import retrofit2.Call
import retrofit2.Response

class Repository {

    suspend fun getLogin(userObject: UserObject): Response<UserObject> {
        return RetrofitInstance.api.getLogin(userObject)
    }

    suspend fun getSignUp(userObject: UserObject): Response<UserObject> {
        return RetrofitInstance.api.getSignUp(userObject)
    }

    suspend fun getDecks(): Call<List<Deck>> {
        return RetrofitInstance.api.getDecks()
    }

    suspend fun addDecks(){

    }

    suspend fun getLobbies(token:String): LobbiesListItem {
        return RetrofitInstance.api.getRooms(token)
    }

    suspend fun getLobby(token:String,roomName:String): Response<LobbyItem>{
        return RetrofitInstance.api.getRoom(token,roomName)
    }

    suspend fun createRoom(token:String,lobbyItem: LobbyItem): Response<LobbyItem>{
        return RetrofitInstance.api.createRoom(token,lobbyItem)
    }

    suspend fun deleteRoom(token:String,lobbyItem: LobbyItem): Response<LobbyItem>{
        return RetrofitInstance.api.deleteRoom(token,lobbyItem)
    }

    suspend fun joinRoom(token:String,lobbyItem: LobbyItem): Response<LobbyItem>{
        return RetrofitInstance.api.joinRoom(token,lobbyItem)
    }

    suspend fun getResult(token:String,roomName: String): Response<ResultItem>{
        return RetrofitInstance.api.getResult(token,roomName)
    }

    suspend fun vote(token:String,voteItem: VoteItem): Response<VoteItem>{
        return RetrofitInstance.api.vote(token,voteItem)
    }

}