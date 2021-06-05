package com.example.proyectoappmoviles.Api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoappmoviles.ObjectItems.Deck
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiViewModel(private val repository: Repository):ViewModel() {

    var myResponse: MutableLiveData<Response<UserObject>> = MutableLiveData()
    var deckResponse: MutableLiveData<Response<DeckObject>> = MutableLiveData()

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

    fun getDecks(){
        viewModelScope.launch {
            deckResponse= MutableLiveData()
            val response: Response<DeckObject> = repository.getDecks()
            deckResponse.value = response
        }
    }
}