package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Adapters.LobbyVotesAdapter
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.VoteItem
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.ViewModels.VotesViewModel

class VoteFragment : Fragment() {
    private lateinit var apiViewModel: ApiViewModel
    lateinit var adapter: LobbyVotesAdapter
    private val viewModel: VotesViewModel by activityViewModels()

    private val args: VoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vote, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.votesRecyclerView)
        recyclerView.setHasFixedSize(true)
        adapter = LobbyVotesAdapter(viewModel.list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})


        val repository= Repository()
        val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val token= prefs?.getString("loggedInToken","").toString()
        val roomName= prefs?.getString("CurrentRoomName","").toString()
        apiViewModel.getResult(token,roomName)
        apiViewModel.getResultResponse.observe(activity as MainActivity, Observer { response->
            Log.d("yes",response.body().toString())
            Log.d("yes",response.code().toString())
        })

        //TODO:hacer un recycler view con la info del jugador y su voto. Esta info es provista por la api
        //TODO:hacer que la extraccion de datos anterior sea cada 5 segundos
        viewModel.updateVotes(
                VoteItem(
                    name = "Me",
                    vote = args.CardNumber,
                    roomName = null,
                    message = null))

        return view
    }
}