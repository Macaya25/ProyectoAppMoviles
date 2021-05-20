package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.R


class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var deck: Array<String>
    private val viewModel: CardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner= view.findViewById<Spinner>(R.id.spinner)
        val spinnerAdapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(view.context, R.array.decks,android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this



        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)
        val btnLogout=view.findViewById<Button>(R.id.LogoutButton)

        btnLogout.setOnClickListener{
            val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor= prefs?.edit()
            editor?.apply {
                putBoolean("loggedIn",false)
            }?.apply()
            Navigation.findNavController(view).popBackStack(R.id.loginFragment,true)
            //Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_loginFragment)
        }


        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_deckFragment)
        }

        btnLobby.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_lobbyFragment)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val deckName = parent?.getItemAtPosition(position)

        if (deckName == "Standard") deck = resources.getStringArray(R.array.Standard)
        else if (deckName == "T-Shirt") deck = resources.getStringArray(R.array.TShirt)
        else if (deckName == "Fibonacci") deck = resources.getStringArray(R.array.Fibonacci)
        else if (deckName == "Hours") deck = resources.getStringArray(R.array.Hours)
        viewModel.setDeck(deck)
    }

}