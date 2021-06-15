package com.example.proyectoappmoviles.Api

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoappmoviles.ObjectItems.*
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
    var myLobby: MutableLiveData<Response<LobbyItem>> = MutableLiveData()
    var createRoomResponse: MutableLiveData<Response<LobbyItem>> = MutableLiveData()
    var deleteRoomResponse: MutableLiveData<Response<LobbyItem>> = MutableLiveData()
    var joinRoomResponse: MutableLiveData<Response<LobbyItem>> = MutableLiveData()
    var getResultResponse: MutableLiveData<Response<ResultItem>> = MutableLiveData()
    var voteResponse: MutableLiveData<Response<VoteItem>> = MutableLiveData()
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
            val response= repository.getLobbies(token)
            myLobbies.value = response
            //val dummy1= ExampleItem("ewe", "OwO 1", Deck(name= "testDeck", listOf("")))
            //val dummy2= ExampleItem("ewe", "OwO 2", Deck(name= "testDeck", listOf("")))
            //val dummy3= ExampleItem("ewe", "OwO 3", Deck(name= "testDeck", listOf("")))
            //val dummyList= LobbiesListItem(listOf(dummy1,dummy2,dummy3))
            //myLobbies.postValue(dummyList)

        }
    }
    //Aun con dummy
    fun getRoom(token:String,roomName:String){
        viewModelScope.launch {
            myLobby= MutableLiveData()
            val dummyCards= listOf("1","2","3")
            val dummyDeck=Deck("T-Shirt",dummyCards)
            val dummyMembers= listOf("Julio","Claudio","Carlos")
            val dummy1=LobbyItem(null,"roomId","roomName",dummyDeck,dummyMembers,null,null,null)
            myLobby.postValue(Response.success(dummy1))
            //val response= repository.getLobby(token,roomName)
            //myLobby.value = response

        }
    }

    fun createRoom(token:String,lobbyItem: LobbyItem){
        viewModelScope.launch {
            createRoomResponse= MutableLiveData()
            val response= repository.createRoom(token,lobbyItem)
            createRoomResponse.value = response
            //val dummy=LobbyItem(null,"123",null,null,null,null,"La sala existe equisde")
            //createRoomResponse.postValue(Response.success(dummy))
        }
    }

    fun deleteRoom(token:String,lobbyItem: LobbyItem){
        viewModelScope.launch {
            deleteRoomResponse= MutableLiveData()
            val response= repository.deleteRoom(token,lobbyItem)
            deleteRoomResponse.value = response
            //val dummy=LobbyItem(null,null,null,null,null,null,"La sala se murio equisde",null)
            //deleteRoomResponse.postValue(Response.success(dummy))

        }
    }

    fun joinRoom(token:String,lobbyItem: LobbyItem,activity: Activity){
        viewModelScope.launch {
            joinRoomResponse= MutableLiveData()
            val response= repository.joinRoom(token,lobbyItem)
            joinRoomResponse.value = response
            val prefs= activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor= prefs?.edit()
            editor?.apply {
                putString("CurrentRoomName",lobbyItem.name)
            }?.apply()
            //val member1="Julio"
            //val member2="Claudio"
            //val member3="Carlos"
            //val dummyMembers= listOf(member1,member2,member3)
            //val dummy=LobbyItem(null,null,null,null,dummyMembers,null,"La sala se murio equisde")
            //joinRoomResponse.postValue(Response.success(dummy))

        }
    }


    fun getResult(token:String,roomName: String){
        viewModelScope.launch {
            getResultResponse= MutableLiveData()
            val response= repository.getResult(token,roomName)
            getResultResponse.value = response
            //val dummyCards= listOf("1","2","3")
            //val dummyDeck=Deck("DeckName",dummyCards)
            //val result1=VoteItem(null,"1",null,"Julio")
            //val result2=VoteItem(null,"1",null,"Claudio")
            //val result3=VoteItem(null,"2",null,"Carlos")
            //val dummyResult= listOf(result1,result2,result3)
            //val dummy = ResultItem("RoomId xd",dummyDeck,dummyResult)
            //getResultResponse.postValue(Response.success(dummy))
        }
    }

    fun vote(token:String,voteItem: VoteItem){
        viewModelScope.launch {
            voteResponse= MutableLiveData()
            val response= repository.vote(token,voteItem)
            voteResponse.value = response
            //val dummy = VoteItem(null,null,"que bacan tu voto xd",null)
            //voteResponse.postValue(Response.success(dummy))
        }
    }

}