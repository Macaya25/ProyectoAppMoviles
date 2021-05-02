package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.ExampleItem

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    var list= mutableListOf<ExampleItem>()
    var genericList = MutableLiveData<MutableList<ExampleItem>>()

    fun addShit(item : ExampleItem){
        list.add(item)
        genericList.postValue(list)
    }
}