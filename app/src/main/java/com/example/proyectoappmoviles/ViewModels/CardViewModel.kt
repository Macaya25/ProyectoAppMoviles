package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.CardItem
import com.example.proyectoappmoviles.R

class CardViewModel(application: Application) : AndroidViewModel(application) {
    var list = mutableListOf<CardItem>()
    var live_list = MutableLiveData<MutableList<CardItem>>()

    var selected_deck: Int = 0

    init{
        setDeck(application.resources.getStringArray(R.array.Standard), 0)
    }

    fun setDeck(deck: Array<String>, deckIndex: Int){
        selected_deck = deckIndex
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