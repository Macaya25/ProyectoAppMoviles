package com.example.proyectoappmoviles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout


class InspectCardFragment : Fragment() {
    lateinit var com:OnFragmentActionsListener
    var displayMessage: String? =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val aux = inflater.inflate(R.layout.fragment_inspect_card, container, false)
        displayMessage= arguments?.getString("Name")
        aux.findViewById<TextView>(R.id.NumberText).text=displayMessage
        return aux
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnCard=view.findViewById<ConstraintLayout>(R.id.InspectCard)
        com=activity as OnFragmentActionsListener

        btnCard.setOnClickListener {
            com.onClickFragmentButton(DeckFragment())
        }
    }
}