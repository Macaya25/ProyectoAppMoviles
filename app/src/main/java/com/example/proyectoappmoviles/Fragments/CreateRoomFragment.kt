package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.*
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.database.RoomEntityMapper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CreateRoomFragment : Fragment() {
    private val viewModel: ContactViewModel by activityViewModels()
    private lateinit var apiViewModel: ApiViewModel

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

                //TODO Poner un spinner que muestre los deck de la bdd en vez de hardcoded
                //TODO: Agregar un spinner para seleccionar el maso y darselo a temp
                val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val token= prefs?.getString("loggedInToken","")
                if (token != null) {
                    val temp=LobbyItem(null,auxtext1,null,null,auxtext2,null)
                    apiViewModel.createRoom(token,temp)
                    apiViewModel.createRoomResponse.observe(activity as MainActivity, Observer { response->
                        Log.d("test", response.body().toString())
                        Log.d("test", response.code().toString())
                    })
                }

                val item = ExampleItem(auxtext1, auxtext2)
                executor.execute{
                    viewModel.database.addRoom(RoomEntityMapper().mapToCached(item))
                }
                viewModel.addRoom(item)
                Navigation.findNavController(view).navigate(R.id.action_createRoomFragment_to_lobbyFragment)
            }
        }
    }
}