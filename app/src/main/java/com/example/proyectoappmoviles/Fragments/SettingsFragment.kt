package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
//import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.database.DeckEntity
import com.example.proyectoappmoviles.database.DeckEntityMapper
import com.example.proyectoappmoviles.database.RoomRepository
import org.koin.android.ext.android.inject


class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var deck: String
    private lateinit var decks: List<DeckEntity>
    private val viewModel: CardViewModel by inject()
    private val apiViewModel:ApiViewModel by inject()
    private val roomViewModel: ContactViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val repository= Repository()
        //val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        //apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)
        val spinner= view.findViewById<Spinner>(R.id.spinner)

        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val usernameaux= prefs?.getString("loggedInUser",null).toString()
        val spinnerIndex= prefs?.getInt(usernameaux+"Deck",0)
        Log.d("spinner index",spinnerIndex.toString())

        viewModel.executor.execute {
            val deckNames = viewModel.getDeckNames().toList()
            val spinnerAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, deckNames)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = this
            spinner.setSelection(spinnerIndex!!)
        }


        
        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)
        val btnLogout=view.findViewById<Button>(R.id.LogoutButton)

        btnLogout.setOnClickListener{

            val usernameaux= prefs?.getString("loggedInUser",null).toString()
            val deckaux = prefs?.getInt(usernameaux+"Deck",0)
            val deckNameaux = prefs?.getString(usernameaux+"DeckName", null).toString()
            val editor= prefs?.edit()
            editor?.apply {
                putBoolean("loggedIn",false)
                putString("loggedInUser",null)
                putString("loggedInPass",null)
                putString("SettingsDeck",null)
                putString("CurrentJoinedRoom",null)
                putString(usernameaux+"DeckName", deckNameaux as String?)
                putInt(usernameaux+"Deck", deckaux!!)
            }?.apply()
            val navOption = NavOptions.Builder().setPopUpTo(R.id.loginFragment,true).build()
            viewModel.executor.execute{
                roomViewModel.database.deleteAllRooms()
            }
            roomViewModel.list.clear()
            roomViewModel.genericList.postValue(roomViewModel.list)
            apiViewModel.myLobbies = MutableLiveData()
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_loginFragment,null,navOption)
        }


        btnDeck.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToDeckFragment(deck)
            Navigation.findNavController(view).navigate(action)
        }

        btnLobby.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToLobbyFragment(deck)
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val deckName = parent?.getItemAtPosition(position)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val usernameaux= prefs?.getString("loggedInUser",null).toString()

        viewModel.executor.execute{
            val editor= prefs?.edit()
            decks = viewModel.deckDao.getAllDecks()
            decks.forEach {
                if (it.name == deckName){
                    this.deck=it.cards
                    Log.d("yes",it.cards)
                    editor?.apply {
                        putString("SettingsDeck", it.cards as String?)
                    }?.apply()
                    //viewModel.setDeck(DeckEntityMapper().mapFromCached(it), position)
                }
            }

            editor?.apply {
                putString(usernameaux+"DeckName", deckName as String?)
                putInt(usernameaux+"Deck", position)
            }?.apply()
        }


    }

}