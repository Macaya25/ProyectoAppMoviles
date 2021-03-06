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
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.clase12.service.LocationService
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
//import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.ObjectItems.LocationItem
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.database.RoomEntityMapper
import org.koin.android.ext.android.inject


class JoinRoomFragment() : Fragment() {
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
                                if (response1.isSuccessful) {
                                    LocationService.getLocation().observe(viewLifecycleOwner, {
                                        val tempLocation = LocationItem(it.latitude.toString(), it.longitude.toString(), null, tempName, null)
                                        //Toast.makeText(activity, it.latitude.toString()+" "+it.longitude.toString(), Toast.LENGTH_SHORT).show()
                                        apiViewModel.reportLocation(token, tempLocation)
                                        apiViewModel.reportLocationResponse.observe(activity as MainActivity, Observer { aux->
                                            if (aux.isSuccessful){
                                                val editor= prefs?.edit()
                                                editor?.apply {
                                                    putString("CurrentJoinedRoom", tempName as String?)
                                                }?.apply()
                                                val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToCardSelectorFragment(
                                                        response1.body()?.deck?.name.toString(),
                                                        response1.body()?.deck?.toString()!!
                                                )
                                                Navigation.findNavController(view).navigate(action)
                                            }
                                        })
                                    })

                                }
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
            val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val deck= prefs?.getString("SettingsDeck","0, 1/2, 1, 2, 3, 5, 8, 13, 20, 40, 100, ???").toString()
            val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToDeckFragment(deck)
            Navigation.findNavController(view).navigate(action)
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