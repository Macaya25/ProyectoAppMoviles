package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.Adapters.VoteAdapter
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.database.DeckEntityMapper


class CardSelectorFragment : Fragment() {

    lateinit var adapter: VoteAdapter
    private val viewModel: CardViewModel by activityViewModels()
    private val args: CardSelectorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_selector, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.DeckRecyclerView)
        recyclerView.setHasFixedSize(true)
        adapter = VoteAdapter(viewModel.list)
        adapter.view = view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})
        viewModel.executor.execute{
            viewModel.setDeck(DeckEntityMapper().mapFromCached(viewModel.deckDao.getDeck(args.deckName)),0)

        }

        return view
    }

}