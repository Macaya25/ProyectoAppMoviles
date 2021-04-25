package com.example.proyectoappmoviles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CardViewModel(application: Application) : AndroidViewModel(application) {
    var list = mutableListOf<CardItem>()
    var live_list = MutableLiveData<MutableList<CardItem>>()

    init{
        setDeck(application.resources.getStringArray(R.array.Standard))
    }

    fun setDeck(deck: Array<String>){
        val cards = deck.toList().chunked(3)
        for(i in cards.indices){
            list.add(CardItem(cards[i].size, cards[i]))
        }
        live_list.postValue(list)
    }
}