package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.net.ConnectivityManager
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
import com.example.proyectoappmoviles.ObjectItems.Deck
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.database.DeckEntity
import com.example.proyectoappmoviles.database.RoomEntity
import com.example.proyectoappmoviles.database.RoomEntityMapper
import okhttp3.internal.wait
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LobbyFragment : Fragment() {

    private lateinit var apiViewModel: ApiViewModel
    lateinit var adapter: ExampleAdapter
    private val viewModel: ContactViewModel by activityViewModels()
    private val cardViewModel: CardViewModel by activityViewModels()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    lateinit var token: String

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
        token= prefs?.getString("loggedInToken","").toString()

        if (check_connection()){
            viewModel.list.clear()
            executor.execute{
            viewModel.database.getAllRooms().forEach{
                    RoomEntityMapper().mapFromCached(it)?.let { it1 ->
                        if(it1.waitingDelete){
                            val tempDel=LobbyItem(null,it1.roomId,it1.roomName,null,null,null,null,null)
                            apiViewModel.deleteRoom(token,tempDel)
                            viewModel.database.deleteRoom(it1.roomName)
                        }
                        if (it1.roomName==it.room_id){
                            //TODO OjO que la clave esta jarcodeada
                            val temp=LobbyItem(null,null, it1.roomName, it1.deck,null,"123",null,null)
                            apiViewModel.createRoom(token,temp)
                            viewModel.database.deleteRoom(it1.roomName)

                        }
                    }
                }

            }


            if (token != null) {
                //viewModel.list.clear()
                //viewModel.genericList.postValue(viewModel.list)
                apiViewModel.getRooms(token)
                apiViewModel.myLobbies.observe(activity as MainActivity, Observer { response->
                    response.rooms.forEach(){
                        executor.execute {
                            if (it !in viewModel.list) {
                                Log.d("xxxxx",it.toString())
                                RoomEntityMapper().mapToCached(it)?.let { it1 -> viewModel.database.addRoom(it1) }
                                viewModel.addRoom(it)
                            }
                        }
                    }
                })
            }

        }else{
            //Todo: Hacer que viewModel.list tenga las salas leidas de la base de datos locas
            viewModel.list.clear()
            executor.execute{
                viewModel.database.getAllRooms().forEach{
                    RoomEntityMapper().mapFromCached(it)?.let { it1 ->
                        if(!it1.waitingDelete){
                            viewModel.addRoom(it1)
                        }
                    }

                }
            }

            viewModel.genericList.postValue(viewModel.list)
            Toast.makeText(activity, "Logged in without internet", Toast.LENGTH_SHORT).show()
        }


        Log.d("list",viewModel.list.toString())
        adapter= ExampleAdapter(viewModel.list, apiViewModel,token,activity as MainActivity,aux)
        val recycler_view = aux.findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter=adapter
        recycler_view.layoutManager= LinearLayoutManager(activity)
        viewModel.genericList.observe(viewLifecycleOwner,androidx.lifecycle.Observer{adapter.set(it)})

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_view)


        //Tester de API
        /*if (token != null) {
            val dummyCards= listOf("0","1","2","3","5","8","13","21","34","55","89","144","∞")
            val dummyDeck= Deck("Fibonacci",dummyCards)
            val dummy=LobbyItem(null,"null",dummyDeck,null,"null",null)
            //val voteDummy= VoteItem("Nombre","XL",null)
            apiViewModel.createRoom(token,dummy)
            apiViewModel.createRoomResponse.observe(activity as MainActivity, Observer { response->
                Log.d("test", response.body().toString())
                Log.d("test", response.code().toString())
            })
        }*/

        return aux
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val tempDel=LobbyItem(null,viewModel.list[viewHolder.adapterPosition].roomId,viewModel.list[viewHolder.adapterPosition].roomName,null,null,null,null,null)
            Log.d("Offline Deletion","Before Deletion")
            if (check_connection()) {
                apiViewModel.deleteRoom(token,tempDel)
                apiViewModel.deleteRoomResponse.observe(activity as MainActivity, Observer { response ->
                    if (response.isSuccessful) {
                        val roomName = viewModel.list[viewHolder.adapterPosition].roomName
                        viewModel.list.removeAt(viewHolder.adapterPosition)
                        adapter.notifyDataSetChanged()
                        viewModel.executor.execute {
                            viewModel.database.deleteRoom(roomName)
                        }
                    }
                })
            }
            else {
                Log.d("Offline Deletion","Offline Deletion")
                val roomId = viewModel.list[viewHolder.adapterPosition].roomId
                viewModel.list.removeAt(viewHolder.adapterPosition)
                adapter.notifyDataSetChanged()
                viewModel.executor.execute {
                    viewModel.database.setToDelete(roomId)
                }
                Log.d("Offline Deletion","End Deletion")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDeck=view.findViewById<Button>(R.id.DeckButton)
        val btnSettings=view.findViewById<Button>(R.id.SettingsButton)
        val btnCreateRoom=view.findViewById<Button>(R.id.CreateRoomButton)
        val btnJoinRoom=view.findViewById<Button>(R.id.JoinRoomButton)

        btnDeck.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_deckFragment)
        }

        btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_settingsFragment)
        }

        btnCreateRoom.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_createRoomFragment)

        }

        btnJoinRoom.setOnClickListener{
            if (check_connection()){
                Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_joinRoomFragment)
            }else{
                Toast.makeText(activity, "Cant Join Rooms Offline", Toast.LENGTH_SHORT).show()
            }

        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
            )
    }

    private fun check_connection() : Boolean{
        val managment = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = managment.activeNetworkInfo
        if (networkInfo != null){
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE){
                return true
            }
        }
        return false
    }
}