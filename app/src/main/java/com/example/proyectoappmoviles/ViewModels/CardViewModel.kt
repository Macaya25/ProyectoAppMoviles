package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.CardItem
import com.example.proyectoappmoviles.ObjectItems.Deck

import com.example.proyectoappmoviles.database.DeckDao
import com.example.proyectoappmoviles.database.DeckEntityMapper
import com.example.proyectoappmoviles.database.RoomRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CardViewModel(application: Application, val deckDao: DeckDao) : AndroidViewModel(application) {


    var list = mutableListOf<CardItem>()
    //var live_list = MutableLiveData<MutableList<CardItem>>()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()
    //var deckDao: DeckDao = RoomRepository(application).getDeckDao()

    var selected_deck: Int = 0


    fun getDeckNames(): MutableList<String> {
        val tempDecks = mutableListOf<String>()
        deckDao.getAllDecks().forEach {
            tempDecks.add(it.name)
        }
        return tempDecks
    }

    fun setDeck(deck: Deck, deckIndex: Int){
        selected_deck = deckIndex
        list.clear()
        val mutableCards = deck.cards.toMutableList()
        mutableCards.add("?")
        mutableCards.add("☕️")
        val cards = mutableCards.chunked(3)
        for(i in cards.indices){
            list.add(CardItem(cards[i].size, cards[i]))
        }
        //live_list.postValue(list)
    }




}