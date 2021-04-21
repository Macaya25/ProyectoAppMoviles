package com.example.proyectoappmoviles

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class LoginFragment : Fragment() {
    lateinit var com:OnFragmentActionsListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogin=view.findViewById<Button>(R.id.LoginButton)
        val txtSignIn= view.findViewById<TextView>(R.id.SignInText)
        com=activity as OnFragmentActionsListener

        btnLogin.setOnClickListener {
            com.onClickFragmentButton(LobbyFragment())
        }

        txtSignIn.setOnClickListener {
            com.onClickFragmentButton(LobbyFragment())
        }
    }
}