package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.database.RoomDao
import com.example.proyectoappmoviles.database.RoomEntity
import com.example.proyectoappmoviles.database.RoomEntityMapper
import com.example.proyectoappmoviles.database.RoomRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    var list= mutableListOf<ExampleItem>()
    var genericList = MutableLiveData<MutableList<ExampleItem>>()
    var database: RoomDao = RoomRepository(application).getRoomDao()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var rooms: List<RoomEntity>

    init{
        executor.execute{
            rooms = database.getAllRooms()
            rooms.forEach {
                RoomEntityMapper().mapFromCached(it)?.let { it1 -> list.add(it1) }
            }
            genericList.postValue(list)
        }
    }

    fun addRoom(item : ExampleItem){
        if(item !in list){
            list.add(item)
            genericList.postValue(list)
        }
    }

    fun updateDB(){
        executor.execute {
            rooms = database.getAllRooms()
            rooms.forEach {
                if(RoomEntityMapper().mapFromCached(it) !in list){
                    database.deleteRoom(it)
                }
            }
        }
    }
}