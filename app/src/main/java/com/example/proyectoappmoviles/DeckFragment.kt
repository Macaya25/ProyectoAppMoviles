package com.example.proyectoappmoviles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class DeckFragment : Fragment() {
    lateinit var com:OnFragmentActionsListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deck, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)
        val btnDebugCard=view.findViewById<Button>(R.id.DebugCard)
        com=activity as OnFragmentActionsListener

        btnSettings.setOnClickListener {
            com.onClickFragmentButton(SettingsFragment())
        }

        btnLobby.setOnClickListener {
            com.onClickFragmentButton(LobbyFragment())
        }

        btnDebugCard.setOnClickListener {
            com.onClickFragmentButton(InspectCardFragment())
        }
    }

}