package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectoappmoviles.R


class CardSelectorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_card_selector, container, false)

        //TODO:Replicar la vista de cartas presente en DeckFragment pero con el deck que la api dicte
        //TODO:Hacer que cuando se haga click se vote de manera automatica por esa carta y que se navege al fragmento VoteFragment


        return view
    }

}