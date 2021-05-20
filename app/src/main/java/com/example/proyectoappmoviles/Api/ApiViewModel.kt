package com.example.proyectoappmoviles.Api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiViewModel(private val repository: Repository):ViewModel() {

    var myResponse: MutableLiveData<Response<UserObject>> = MutableLiveData()

    fun getLogin(userObject: UserObject){
        viewModelScope.launch {
            myResponse= MutableLiveData()
            val response: Response<UserObject> = repository.getLogin(userObject)
            myResponse.value = response
        }
    }

    fun getSignUp(userObject: UserObject){
        viewModelScope.launch {
            myResponse= MutableLiveData()
            val response: Response<UserObject> = repository.getSignUp(userObject)
            myResponse.value = response
        }
    }
}