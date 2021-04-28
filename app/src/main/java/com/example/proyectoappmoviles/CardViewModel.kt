package com.example.proyectoappmoviles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CardViewModel(application: Application) : AndroidViewModel(application) {
    var list = mutableListOf<CardItem>()
    var live_list = MutableLiveData<MutableList<CardItem>>()


    fun setDeck(deck: Array<String>){
        list.clear()
        val mutableCards = deck.toMutableList()
        mutableCards.add("?")
        mutableCards.add(9749.toChar().toString())
        val cards = mutableCards.chunked(3)
        for(i in cards.indices){
            list.add(CardItem(cards[i].size, cards[i]))
        }
        live_list.postValue(list)
    }


}