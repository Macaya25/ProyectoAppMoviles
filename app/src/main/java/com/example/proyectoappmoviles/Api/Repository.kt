package com.example.proyectoappmoviles.Api

import retrofit2.Response

class Repository {

    suspend fun getLogin(userObject: UserObject): Response<UserObject> {
        return RetrofitInstance.api.getLogin(userObject)
    }

    suspend fun getSignUp(userObject: UserObject): Response<UserObject> {
        return RetrofitInstance.api.getSignUp(userObject)
    }
}