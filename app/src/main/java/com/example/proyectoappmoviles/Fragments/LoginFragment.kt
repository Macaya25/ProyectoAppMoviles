package com.example.proyectoappmoviles.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.Api.UserObject
import com.example.proyectoappmoviles.R

class LoginFragment : Fragment() {

    private lateinit var apiViewModel:ApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository= Repository()
        val viewModelFactory=ApiViewModelFactory(repository)
        apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)

        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val loggedIn= prefs?.getBoolean("loggedIn",false)
        val usernameaux= prefs?.getString("loggedInUser",null).toString()
        val passwordaux= prefs?.getString("loggedInPass",null).toString()


        if (loggedIn==true){
            val myUser=UserObject(null,usernameaux,null,passwordaux,null)
            apiViewModel.getLogin(myUser)
            apiViewModel.myResponse.observe(activity as MainActivity, Observer { response->
                if(response.isSuccessful){
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)
                }
                else{
                    Toast.makeText(activity as MainActivity,"Incorrect AutoLogin", Toast.LENGTH_SHORT).show()
                }
            })

        }

        /*if (loggedIn==true){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)
        }*/

        val btnLogin=view.findViewById<Button>(R.id.RegisterButton)
        val txtSignIn= view.findViewById<TextView>(R.id.SignInText)

        btnLogin.setOnClickListener {
            val username=view.findViewById<EditText>(R.id.UsernameRegisterPlainText).text.toString()
            val password=view.findViewById<EditText>(R.id.PasswordRegisterPlainText).text.toString()

            val myUser=UserObject(null,username,null,password,null)
            apiViewModel.getLogin(myUser)
            apiViewModel.myResponse.observe(activity as MainActivity, Observer { response->
                if(response.isSuccessful){

                    val editor= prefs?.edit()
                    editor?.apply {
                        putBoolean("loggedIn",true)
                        putString("loggedInUser",username)
                        putString("loggedInPass",password)
                    }?.apply()

                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)
                }
                else{
                    Toast.makeText(activity as MainActivity,"Incorrect Username or Password", Toast.LENGTH_SHORT).show()
                }
            })
        }


        txtSignIn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}