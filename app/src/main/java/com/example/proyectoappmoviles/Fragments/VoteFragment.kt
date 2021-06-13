package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectoappmoviles.R

class VoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_vote, container, false)

        //TODO:hacer un recycler view con la info del jugador y su voto. Esta info es provista por la api
        //TODO:hacer que la extraccion de datos anterior sea cada 5 segundos

        return view
    }
}