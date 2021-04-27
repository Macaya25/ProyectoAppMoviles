package com.example.proyectoappmoviles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.activityViewModels


class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var com:OnFragmentActionsListener
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
        val spinnerAdapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(view.context,R.array.decks,android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this



        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)
        com=activity as OnFragmentActionsListener

        btnDeck.setOnClickListener {
            com.onClickFragmentButton(DeckFragment())
        }

        btnLobby.setOnClickListener {
            com.onClickFragmentButton(LobbyFragment())
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