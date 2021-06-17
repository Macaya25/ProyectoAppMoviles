package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.*
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.Deck
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.database.DeckEntityMapper
import com.example.proyectoappmoviles.database.RoomEntity
import com.example.proyectoappmoviles.database.RoomEntityMapper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CreateRoomFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val viewModel: ContactViewModel by activityViewModels()
    private val deckViewModel: CardViewModel by activityViewModels()
    private lateinit var apiViewModel: ApiViewModel
    private lateinit var selectedDeck: Deck
    private var liveDeck = MutableLiveData<Deck>()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository= Repository()
        val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)


        super.onViewCreated(view, savedInstanceState)
        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnBackToLooby=view.findViewById<Button>(R.id.CreateRoom)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)

        executor.execute{
            val deckNames = deckViewModel.deckDao.getAllDecks().map{ it.name }.toTypedArray()
            val spinner = view.findViewById<Spinner>(R.id.deckSpinner)
            val spinnerAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, deckNames)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = this
        }


        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_createRoomFragment_to_deckFragment)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_createRoomFragment_to_settingsFragment)
        }

        btnLobby.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_createRoomFragment_to_lobbyFragment)
        }

        btnBackToLooby.setOnClickListener {
            val auxtext1=view.findViewById<EditText>(R.id.RoomNamePlainText).text.toString()
            val auxtext2=view.findViewById<EditText>(R.id.RoomPasswordPlainText).text.toString()

            if(auxtext1=="" || auxtext2==""){
                Toast.makeText(activity,"Please Don't Leave Any Input Blank",Toast.LENGTH_SHORT).show()
            }else{
                val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val token= prefs?.getString("loggedInToken","")
                if (token != null) {
                    val temp=LobbyItem(null,null, auxtext1, liveDeck.value,null,auxtext2,null,null)
                    apiViewModel.createRoom(token,temp)
                    apiViewModel.createRoomResponse.observe(activity as MainActivity, Observer { response->
                        if (response.isSuccessful){
                            Navigation.findNavController(view).navigate(R.id.action_createRoomFragment_to_lobbyFragment)
                        }
                        else{
                            viewModel.executor.execute{
                                val room = RoomEntity("", temp.name, false, DeckEntityMapper().mapToCached(temp.deck))
                                viewModel.database.addRoom(room)
                                viewModel.addRoom(RoomEntityMapper().mapFromCached(room))
                            }
                            Navigation.findNavController(view).navigate(R.id.action_createRoomFragment_to_lobbyFragment)
                        }
                    })
                }

            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val deckName = parent?.getItemAtPosition(position)
        if (deckName is String){
            executor.execute{
                selectedDeck = DeckEntityMapper().mapFromCached(deckViewModel.deckDao.getDeck(deckName))
                liveDeck.postValue(selectedDeck)
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}