package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.*
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Adapters.ExampleAdapter
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.database.RoomEntityMapper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LobbyFragment : Fragment() {

    private lateinit var apiViewModel: ApiViewModel
    lateinit var adapter: ExampleAdapter
    private val viewModel: ContactViewModel by activityViewModels()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val aux=inflater.inflate(R.layout.fragment_lobby, container, false)

        val repository= Repository()
        val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val token= prefs?.getString("loggedInToken","")
        if (token != null) {
            apiViewModel.getRooms(token)
            apiViewModel.myLobbies.observe(activity as MainActivity, Observer { response->
                response.rooms.forEach(){
                    executor.execute {
                        viewModel.database.addRoom(RoomEntityMapper().mapToCached(it))
                        viewModel.addRoom(it)
                    }
                }

            })
        }


        adapter= ExampleAdapter(viewModel.list)
        val recycler_view = aux.findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter=adapter
        recycler_view.layoutManager= LinearLayoutManager(activity)
        viewModel.genericList.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_view)

        return aux
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            executor.execute{
                viewModel.updateDB()
            }
            viewModel.list.removeAt(viewHolder.adapterPosition)
            adapter.notifyDataSetChanged()
            //Toast.makeText(activity,"ewe", Toast.LENGTH_SHORT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnCreateRoom=view.findViewById<Button>(R.id.CreateRoomButton)

        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_deckFragment)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_settingsFragment)
        }

        btnCreateRoom.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_createRoomFragment)
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
            )
    }
}