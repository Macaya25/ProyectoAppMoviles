package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clase12.service.LocationService
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
    private var l1:Double=0.0
    private var l2:Double=0.0

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

            //viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})
            viewModel.executor.execute{
                val recyclerView = view.findViewById<RecyclerView>(R.id.DeckRecyclerView)
                recyclerView.setHasFixedSize(true)
                adapter = VoteAdapter(viewModel.list,apiViewModel,token,activity as MainActivity,view,this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(activity)
                viewModel.setDeck(DeckEntityMapper().mapFromCached(viewModel.deckDao.getDeck(args.deckName)),0)
            }
        }

        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)

        btnDeck.setOnClickListener {
            val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val deck= prefs?.getString("SettingsDeck","0, 1/2, 1, 2, 3, 5, 8, 13, 20, 40, 100, ???").toString()
            val action = CardSelectorFragmentDirections.actionCardSelectorFragmentToDeckFragment(deck)
            Navigation.findNavController(view).navigate(action)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_cardSelectorFragment_to_settingsFragment)
        }

        btnLobby.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_cardSelectorFragment_to_lobbyFragment)
        }

        requireActivity()
                .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                    }
                }
                )


        return view
    }


}