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
                val temp = LobbyItem(null,null,tempName,null,null,tempPass,null,null)
                apiViewModel.joinRoom(token,temp,activity as MainActivity)
                apiViewModel.joinRoomResponse.observe(activity as MainActivity, Observer { response->
                    Log.d("yes",response.body().toString())
                    Log.d("yes",response.code().toString())
                    if (response.isSuccessful){
                        apiViewModel.getRoom(token,tempName)
                        apiViewModel.myLobby.observe(activity as MainActivity, Observer { response1->
                            Log.d("yes",response1.body().toString())
                            val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToCardSelectorFragment(
                                    response1.body()?.deck?.name.toString()
                            )
                            Navigation.findNavController(view).navigate(action)
                        })
                    }
                })
            }

        }

        return view
    }

}