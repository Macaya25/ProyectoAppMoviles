package com.example.proyectoappmoviles.Api

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoappmoviles.ObjectItems.Deck
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ObjectItems.LobbiesListItem
import com.example.proyectoappmoviles.database.DeckDao
import com.example.proyectoappmoviles.database.DeckEntityMapper
import com.example.proyectoappmoviles.database.RoomRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ApiViewModel(application: Application, private val repository: Repository): ViewModel() {

    var myResponse: MutableLiveData<Response<UserObject>> = MutableLiveData()
    var myLobbies: MutableLiveData<LobbiesListItem> = MutableLiveData()
    var deckDao: DeckDao = RoomRepository(application).getDeckDao()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

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
                        executor.execute{
                            body.forEach {
                                deckDao.addDeck(DeckEntityMapper().mapToCached(it))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Deck>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun getRooms(token:String){
        viewModelScope.launch {
            myLobbies= MutableLiveData()
            //val response= repository.getLobbies(token)
            val dummy1= ExampleItem("ewe", "OwO", null,null)
            val dummy2= ExampleItem("ewe", "OwO", null,null)
            val dummy3= ExampleItem("ewe", "OwO", null,null)
            val dummyList= LobbiesListItem(listOf(dummy1,dummy2,dummy3))
            myLobbies.postValue(dummyList)
            //myLobbies.value = response
        }
    }

}