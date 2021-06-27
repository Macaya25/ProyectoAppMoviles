package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.*
import com.example.proyectoappmoviles.Adapters.CardAdapter
import com.example.proyectoappmoviles.Interfaces.OnClickFragmentCardInspect
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.ObjectItems.Deck
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import org.koin.android.ext.android.inject


class DeckFragment : Fragment() {
    lateinit var adapter: CardAdapter
    private val args: DeckFragmentArgs by navArgs()
    private val viewModel: CardViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_deck, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.DeckRecyclerView)
        recyclerView.setHasFixedSize(true)
        adapter = CardAdapter(viewModel.list)
        adapter.view = view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val cards = args.cards.split(",").map{ it.trim() }
        val deck = Deck("", cards)
        viewModel.setDeck(deck, 0)
        //viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)
        val card1 = view.findViewById<Button>(R.id.card1)
        val card2 = view.findViewById<Button>(R.id.card2)
        val card3 = view.findViewById<Button>(R.id.card3)


        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_deckFragment_to_settingsFragment)
        }

        btnLobby.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_deckFragment_to_lobbyFragment)
        }




    }

}