package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Adapters.VoteAdapter
import com.example.proyectoappmoviles.Api.ApiViewModel
//import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.database.DeckEntityMapper
import org.koin.android.ext.android.inject


class CardSelectorFragment : Fragment() {

    lateinit var adapter: VoteAdapter
    private val viewModel: CardViewModel by inject()
    private val apiViewModel: ApiViewModel by inject()
    private val args: CardSelectorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_selector, container, false)
        //val repository= Repository()
        //val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        //apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val token= prefs?.getString("loggedInToken","")

        if (token != null) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.DeckRecyclerView)
            recyclerView.setHasFixedSize(true)
            adapter = VoteAdapter(viewModel.list,apiViewModel,token,activity as MainActivity,view)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            //viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})
            viewModel.executor.execute{
                viewModel.setDeck(DeckEntityMapper().mapFromCached(viewModel.deckDao.getDeck(args.deckName)),0)
            }
        }

        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)

        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_cardSelectorFragment_to_deckFragment)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_cardSelectorFragment_to_settingsFragment)
        }


        return view
    }

}