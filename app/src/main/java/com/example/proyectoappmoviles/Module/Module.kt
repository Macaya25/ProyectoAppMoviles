package com.example.proyectoappmoviles.Module

import android.app.Application
import androidx.room.Room
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.ViewModels.VotesViewModel
import com.example.proyectoappmoviles.database.DeckDao
import com.example.proyectoappmoviles.database.RoomRepository
import com.example.proyectoappmoviles.database.RoomsDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule= module {
    //ApiViewModel
    single { Room.databaseBuilder(get(), RoomsDatabase::class.java, "roomsDb").build() }
    single { RoomRepository(get()) }
    single { get<RoomsDatabase>().deckDao() }
    single { get<RoomsDatabase>().roomDao() }
    single {Repository()}
    viewModel {ApiViewModel(Application(),get(),get())}

    //ContactViewModel
    viewModel { ContactViewModel(Application(),get())}

    //CardViewModel
    viewModel { CardViewModel(Application(),get()) }

    //VoteViewModel
    viewModel { VotesViewModel(Application()) }
}