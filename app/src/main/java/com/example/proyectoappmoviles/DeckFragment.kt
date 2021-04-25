package com.example.proyectoappmoviles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})

        return view
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