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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.database.RoomEntityMapper


class JoinRoomFragment : Fragment() {
    private lateinit var apiViewModel: ApiViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_join_room, container, false)

        //TODO:Agregar 2 edit text
        //TODO:Hacer que los valores de los cuadros de texto y spinner los chupe un temp LobbyItem
        //TODO:Mandarle mensaje a la api y cambiar a CardSelectorFragment

        val repository= Repository()
        val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val token= prefs?.getString("loggedInToken","")

        val btnJoinRoom=view.findViewById<Button>(R.id.JoinRoom)
        btnJoinRoom.setOnClickListener{
            if (token != null) {
                val tempName=view.findViewById<EditText>(R.id.JoinRoomNamePlainText).text.toString()
                val tempPass=view.findViewById<EditText>(R.id.JoinRoomPasswordPlainText).text.toString()
                val temp = LobbyItem(null,null,null,null,null,tempPass,null,tempName)
                apiViewModel.joinRoom(token,temp,activity as MainActivity)
                apiViewModel.joinRoomResponse.observe(activity as MainActivity, Observer { response->
                    if (response.isSuccessful){
                        apiViewModel.getRooms(token)
                        apiViewModel.myLobbies.observe(activity as MainActivity, Observer { response->
                            Log.d("xxxxx",response.toString())
                            response.rooms.forEach(){
                                if (it.roomName==tempName){
                                    val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToCardSelectorFragment(
                                            it.deck.name.toString()
                                    )
                                    Navigation.findNavController(view).navigate(action)
                                }
                            }

                        })
                    }
                })
            }

        }

        return view
    }

}