package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.proyectoappmoviles.*
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ViewModels.ContactViewModel

class CreateRoomFragment : Fragment() {
    lateinit var com: OnFragmentActionsListener
    private val viewModel: ContactViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnBackToLooby=view.findViewById<Button>(R.id.BackToLobbyButtton)


        com=activity as OnFragmentActionsListener

        btnDeck.setOnClickListener {
            com.onClickFragmentButton(DeckFragment())
        }

        btnSettings.setOnClickListener {
            com.onClickFragmentButton(SettingsFragment())
        }

        btnBackToLooby.setOnClickListener {
            val auxtext1=view.findViewById<EditText>(R.id.RoomNamePlainText).text.toString()
            val auxtext2=view.findViewById<EditText>(R.id.RoomPasswordPlainText).text.toString()

            if(auxtext1=="" || auxtext2==""){
                Toast.makeText(activity,"Please Don't Leave Any Input Blank",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addShit(ExampleItem(auxtext1))
                com.onClickFragmentButton(LobbyFragment())
            }
        }
    }
}