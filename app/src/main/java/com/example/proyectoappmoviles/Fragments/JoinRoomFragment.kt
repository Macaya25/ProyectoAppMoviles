package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.R


class JoinRoomFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_join_room, container, false)

        //TODO:Agregar 2 edit text y un spiner para seleccionar sala
        //TODO:Hacer que los valores de los cuadros de texto y spinner los chupe un temp LobbyItem
        //TODO:Mandarle mensaje a la api y cambiar a CardSelectorFragment

        val btnJoinRoom=view.findViewById<Button>(R.id.JoinRoom)
        btnJoinRoom.setOnClickListener{

            val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToCardSelectorFragment(
                "Standard"
            )
            Navigation.findNavController(view).navigate(action)
        }

        return view
    }

}