package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.*
import com.example.proyectoappmoviles.Adapters.ExampleAdapter
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.ViewModels.ContactViewModel

class LobbyFragment : Fragment() {

    lateinit var com: OnFragmentActionsListener
    lateinit var adapter: ExampleAdapter
    private val viewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val aux=inflater.inflate(R.layout.fragment_lobby, container, false)
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
        com=activity as OnFragmentActionsListener

        btnDeck.setOnClickListener {
            com.onClickFragmentButton(DeckFragment())
        }

        btnSettings.setOnClickListener {
            com.onClickFragmentButton(SettingsFragment())
        }

        btnCreateRoom.setOnClickListener {
            com.onClickFragmentButton(CreateRoomFragment())
        }
    }
}