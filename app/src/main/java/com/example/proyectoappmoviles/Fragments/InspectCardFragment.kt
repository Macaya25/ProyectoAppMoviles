package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.R


class InspectCardFragment : Fragment() {
    val args:InspectCardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val aux = inflater.inflate(R.layout.fragment_inspect_card, container, false)
        aux.findViewById<TextView>(R.id.NumberText).text=args.cardName
        return aux
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnCard=view.findViewById<ConstraintLayout>(R.id.InspectCard)
        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)

        btnCard.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_inspectCardFragment_to_deckFragment)
        }

        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_inspectCardFragment_to_deckFragment)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_inspectCardFragment_to_settingsFragment)
        }

        btnLobby.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_inspectCardFragment_to_lobbyFragment)
        }

    }
}