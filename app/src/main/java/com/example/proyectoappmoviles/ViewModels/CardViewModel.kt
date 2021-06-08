package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.CardItem
import com.example.proyectoappmoviles.ObjectItems.Deck

import com.example.proyectoappmoviles.database.DeckDao
import com.example.proyectoappmoviles.database.DeckEntityMapper
import com.example.proyectoappmoviles.database.RoomRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CardViewModel(application: Application) : AndroidViewModel(application) {


    var list = mutableListOf<CardItem>()
    var live_list = MutableLiveData<MutableList<CardItem>>()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()
    var deckDao: DeckDao = RoomRepository(application).getDeckDao()

    var selected_deck: Int = 0

    init{
        //createDecks()
        executor.execute{
            //setDeck(DeckEntityMapper().mapFromCached(deckDao.getAllDecks()[0]), 0)
        }
    }

    fun setDeck(deck: Deck, deckIndex: Int){
        selected_deck = deckIndex
        list.clear()
        val mutableCards = deck.cards.toMutableList()
        mutableCards.add("?")
        mutableCards.add(9749.toChar().toString())
        val cards = mutableCards.chunked(3)
        for(i in cards.indices){
            list.add(CardItem(cards[i].size, cards[i]))
        }
        live_list.postValue(list)
    }


    private fun createDecks(){
        val standard = Deck("Standard", listOf("0","½", "1", "2", "3", "4", "5", "6",
                                                           "7", "8","13","20","40","100","∞"))
        val fibonacci = Deck("Fibonacci", listOf("0", "1", "2", "3","5","8","13",
                                                            "21", "34", "55", "89","144","∞"))
        val tShirt = Deck("T-Shirt", listOf("XS","S","M","L","XL","XXL"))

        val hours = Deck("Hours", listOf("0","1","2","3","4","6","8","12",
                                                    "16","24","32","40"))
        executor.execute{
            deckDao.addDeck(DeckEntityMapper().mapToCached(standard))
            deckDao.addDeck(DeckEntityMapper().mapToCached(fibonacci))
            deckDao.addDeck(DeckEntityMapper().mapToCached(tShirt))
            deckDao.addDeck(DeckEntityMapper().mapToCached(hours))
        }

    }



}