package com.example.proyectoappmoviles.Api

import com.example.proyectoappmoviles.ObjectItems.Deck
import com.example.proyectoappmoviles.ObjectItems.LobbiesListItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
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

}