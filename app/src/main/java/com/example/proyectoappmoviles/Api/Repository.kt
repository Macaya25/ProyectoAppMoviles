package com.example.proyectoappmoviles.Api

import com.example.proyectoappmoviles.ObjectItems.Deck
import retrofit2.Response

class Repository {

    suspend fun getLogin(userObject: UserObject): Response<UserObject> {
        return RetrofitInstance.api.getLogin(userObject)
    }

    suspend fun getSignUp(userObject: UserObject): Response<UserObject> {
        return RetrofitInstance.api.getSignUp(userObject)
    }

    suspend fun getDecks(): Response<DeckObject> {
        return RetrofitInstance.api.getDecks()
    }
}