package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clase12.service.LocationService
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Adapters.LobbyVotesAdapter
import com.example.proyectoappmoviles.Api.ApiViewModel
//import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.ObjectItems.LocationItem
import com.example.proyectoappmoviles.ObjectItems.MemberItem
import com.example.proyectoappmoviles.ObjectItems.VoteItem
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.ViewModels.VotesViewModel
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.util.*

class VoteFragment : Fragment() {
    private val apiViewModel: ApiViewModel by inject()
    lateinit var adapter: LobbyVotesAdapter
    private val viewModel: VotesViewModel by inject()
    lateinit var mainHandler: Handler

    private val args: VoteFragmentArgs by navArgs()

    private val updateMembersNVotesTask = object : Runnable {
        override fun run() {
            updateMembersNVotes()
            mainHandler.postDelayed(this, 5000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vote, container, false)

        //val repository= Repository()
        //val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        //apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)


        /*viewModel.updateVotes(
                VoteItem(
                        name = "Me",
                        vote = args.CardNumber,
                        roomName = null,
                        message = null)
        )*/

        //TODO:hacer un recycler view con la info del jugador y su voto. Esta info es provista por la api
        //TODO:hacer que la extraccion de datos anterior sea cada 5 segundos
        mainHandler = Handler(Looper.getMainLooper())

        val recyclerView = view.findViewById<RecyclerView>(R.id.votesRecyclerView)
        recyclerView.setHasFixedSize(true)
        adapter = LobbyVotesAdapter(viewModel.list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.live_list.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})

        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnLobby=view.findViewById<Button>(R.id.LobbyButton)

        btnDeck.setOnClickListener {
            val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val deck= prefs?.getString("SettingsDeck","0, 1/2, 1, 2, 3, 5, 8, 13, 20, 40, 100, ∞").toString()
            val action = VoteFragmentDirections.actionVoteFragmentToDeckFragment(deck)
            Navigation.findNavController(view).navigate(action)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_voteFragment_to_settingsFragment)
        }

        btnLobby.setOnClickListener {
            //Navigation.findNavController(view).navigate(R.id.action_voteFragment_to_cardSelectorFragment)
            val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val token= prefs?.getString("loggedInToken","")
            val tempName=prefs?.getString("CurrentJoinedRoom","")
            apiViewModel.getRoom(token!!, tempName!!)
            apiViewModel.myLobby.observe(activity as MainActivity, Observer { response1 ->
                val action = VoteFragmentDirections.actionVoteFragmentToCardSelectorFragment(
                        response1.body()?.deck?.name.toString(),
                        response1.body()?.deck?.toString()!!
                )
                Navigation.findNavController(view).navigate(action)
            })
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

    fun updateMembersNVotes(){
        if (check_connection()) {
            Log.d("looper", "LOOOOOOP")
            val prefs = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val token = prefs?.getString("loggedInToken", "").toString()
            val roomName = prefs?.getString("CurrentRoomName", "").toString()
            //TODO:Rehacer el la funcion que regista los votos para que ahora pueda funcionar con las coordenadas
            apiViewModel.getRoom(token, roomName)
            apiViewModel.myLobby.observe(activity as MainActivity, Observer { response ->
                if (response.isSuccessful) {
                    apiViewModel.getResult(token, roomName)
                    apiViewModel.getResultResponse.observe(activity as MainActivity, Observer { response1 ->
                        if (response1.isSuccessful) {
                            viewModel.setMemberList(response.body()?.members!!, response1.body()!!.result)
                            //Log.d("members", viewModel.list.toString())
                        }
                    })
                }
            })
            LocationService.getLocation().observe(viewLifecycleOwner, {
                val tempLocation = LocationItem(it.latitude.toString(), it.longitude.toString(), null, roomName, null)
                //Toast.makeText(activity, it.latitude.toString()+" "+it.longitude.toString(), Toast.LENGTH_SHORT).show()
                apiViewModel.reportLocation(token, tempLocation)
            })

            Log.d("looper", "END")
        }else{
            Toast.makeText(activity, "ERROR: Cant Refresh Without Internet", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateMembersNVotesTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateMembersNVotesTask)
    }

    private fun check_connection() : Boolean{
        val managment = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = managment.activeNetworkInfo
        if (networkInfo != null){
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE){
                return true
            }
        }
        return false
    }

}