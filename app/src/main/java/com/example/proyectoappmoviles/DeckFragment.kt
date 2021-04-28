package com.example.proyectoappmoviles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DeckFragment : Fragment() {
    lateinit var com:OnFragmentActionsListener
    lateinit var adapter: CardAdapter

    private val viewModel: CardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_deck, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.DeckRecyclerView)
        recyclerView.setHasFixedSize(true)
        adapter = CardAdapter(viewModel.list)
        adapter.com= activity as OnClickFragmentCardInspect
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)
        val card1 = view.findViewById<Button>(R.id.card1)
        val card2 = view.findViewById<Button>(R.id.card2)
        val card3 = view.findViewById<Button>(R.id.card3)

        com=activity as OnFragmentActionsListener

        btnSettings.setOnClickListener {
            com.onClickFragmentButton(SettingsFragment())
        }

        btnLobby.setOnClickListener {
            com.onClickFragmentButton(LobbyFragment())
        }




    }

}