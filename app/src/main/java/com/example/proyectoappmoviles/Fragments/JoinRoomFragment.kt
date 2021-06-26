package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
//import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.database.RoomEntityMapper
import org.koin.android.ext.android.inject


class JoinRoomFragment : Fragment() {
    private val apiViewModel: ApiViewModel by inject()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_join_room, container, false)

        //val repository= Repository()
        //val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        //apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val token= prefs?.getString("loggedInToken","")

        val btnJoinRoom=view.findViewById<Button>(R.id.JoinRoom)
        btnJoinRoom.setOnClickListener{
            if (check_connection()){
                if (token != null) {
                    val tempName=view.findViewById<EditText>(R.id.JoinRoomNamePlainText).text.toString()
                    val tempPass=view.findViewById<EditText>(R.id.JoinRoomPasswordPlainText).text.toString()
                    val temp = LobbyItem(null,null,tempName,null,null,tempPass,null,null)
                    apiViewModel.joinRoom(token,temp,activity as MainActivity)
                    apiViewModel.joinRoomResponse.observe(activity as MainActivity, Observer { response ->
                        Log.d("yes", response.body().toString())
                        Log.d("yes", response.code().toString())
                        if (response.isSuccessful && response.body()?.message != null) {
                            apiViewModel.getRoom(token, tempName)
                            apiViewModel.myLobby.observe(activity as MainActivity, Observer { response1 ->
                                Log.d("yes", response1.body().toString())
                                val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToCardSelectorFragment(
                                        response1.body()?.deck?.name.toString()
                                )
                                Navigation.findNavController(view).navigate(action)
                            })
                        } else {
                            Toast.makeText(activity,"No Match For Given Credentials", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }else{
                Toast.makeText(activity, "Cant Join Rooms Offline", Toast.LENGTH_SHORT).show()
            }

        }

        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)

        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_joinRoomFragment_to_deckFragment)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_joinRoomFragment_to_settingsFragment)
        }

        return view
    }

    private fun check_connection() : Boolean{
        val managment = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = managment.activeNetworkInfo
        if (networkInfo != null){
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE){
                return true
            }
        }
        return false
    }

}