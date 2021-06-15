package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.VoteItem

class VotesViewModel(application: Application) : AndroidViewModel(application) {

    var list = mutableListOf<VoteItem>()
    var live_list = MutableLiveData<MutableList<VoteItem>>()

    fun updateVotes(myVote: VoteItem){
        list.clear()
        //TODO: Hacer call a api de result

        list.add(myVote)
        live_list.postValue(list)
    }
}