package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.database.RoomDao
import com.example.proyectoappmoviles.database.RoomEntityMapper
import com.example.proyectoappmoviles.database.RoomRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    var list= mutableListOf<ExampleItem>()
    var genericList = MutableLiveData<MutableList<ExampleItem>>()
    var database: RoomDao = RoomRepository(application).getRoomDao()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var rooms: List<ExampleItem>

    init{
        executor.execute{
            rooms = database.getAllRooms().map { RoomEntityMapper().mapFromCached(it) }
            rooms.forEach {
                list.add(it)
            }
            genericList.postValue(list)
        }
    }

    fun addRoom(item : ExampleItem){
        list.add(item)
        genericList.postValue(list)
    }

    fun saveDB(){
        executor.execute {
            rooms = database.getAllRooms().map{RoomEntityMapper().mapFromCached(it)}
            rooms.forEach {
                if(it !in list){
                    //database.deleteContact(ContactEntityMapper().mapToCached(it))
                }
            }
        }
    }
}