package com.example.proyectoappmoviles.Api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoappmoviles.ObjectItems.Deck
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiViewModel(private val repository: Repository): ViewModel() {

    var myResponse: MutableLiveData<Response<UserObject>> = MutableLiveData()

    init {
        getDecks()
    }


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
            val call: Call<List<Deck>> = repository.getDecks()
            call.enqueue(object : Callback<List<Deck>> {
                override fun onResponse(call: Call<List<Deck>>, response: Response<List<Deck>>) {
                    val body = response.body()
                    if(body != null) {
                        body.forEach {
                            TODO("Aqui deberia llamarse al dao para agregar los deck a la bdd")
                            println(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Deck>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}