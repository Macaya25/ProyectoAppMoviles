package com.example.proyectoappmoviles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout


class InspectCardFragment : Fragment() {
    lateinit var com:OnFragmentActionsListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspect_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnCard=view.findViewById<ConstraintLayout>(R.id.InspectCard)
        com=activity as OnFragmentActionsListener

        btnCard.setOnClickListener {
            com.onClickFragmentButton(DeckFragment())
        }
    }
}