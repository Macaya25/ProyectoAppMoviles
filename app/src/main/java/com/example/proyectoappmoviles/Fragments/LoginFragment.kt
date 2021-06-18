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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.Api.UserObject
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.ViewModels.CardViewModel
import com.example.proyectoappmoviles.ViewModels.ContactViewModel
import com.example.proyectoappmoviles.database.DeckEntity
import com.example.proyectoappmoviles.database.DeckEntityMapper
import java.lang.Exception
import java.time.LocalDate

class LoginFragment : Fragment() {

    private lateinit var apiViewModel:ApiViewModel
    private val deckViewModel: ContactViewModel by activityViewModels()
    private val viewModel: CardViewModel by activityViewModels()
    private lateinit var deck: Array<String>
    private lateinit var decks: List<DeckEntity>

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
        val viewModelFactory=ApiViewModelFactory(requireActivity().application, repository)
        apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)

        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val loggedIn= prefs?.getBoolean("loggedIn",false)
        val usernameaux= prefs?.getString("loggedInUser",null).toString()
        val passwordaux= prefs?.getString("loggedInPass",null).toString()


        if (loggedIn==true){
            val deckaux= prefs?.getInt(usernameaux+"Deck",0)
            viewModel.executor.execute{
                decks = viewModel.deckDao.getAllDecks()
                decks.forEach {
                    if (it.name == "T-Shirt"){
                        viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 1)
                    }
                    else if (it.name == "Fibonacci"){
                        viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 2)
                    }
                    else if (it.name == "Hours"){
                        viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 3)
                    }
                    else {
                        viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 0)
                    }
                }
            }
            if(check_connection()) {
                val myUser = UserObject(null, usernameaux, null, passwordaux, null)
                apiViewModel.getLogin(myUser)
                apiViewModel.myResponse.observe(activity as MainActivity, Observer { response ->
                    if (response.isSuccessful) {
                        try {
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)
                        }catch (e:Exception){}

                    } else {
                        Toast.makeText(activity as MainActivity, "Incorrect AutoLogin", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Log.d("Offline enter","Offline enter")
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)

                Toast.makeText(activity as MainActivity,"AutoLogin Offline Mode",Toast.LENGTH_SHORT).show()
            }
        }

        /*if (loggedIn==true){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)
        }*/

        val btnLogin=view.findViewById<Button>(R.id.RegisterButton)
        val txtSignIn= view.findViewById<TextView>(R.id.SignInText)

        btnLogin.setOnClickListener {
            if(check_connection()){
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
                            putString("loggedInToken", response.body()?.token.toString())
                        }?.apply()

                        val deckaux= prefs?.getInt(username+"Deck",0)

                        viewModel.executor.execute{
                            decks = viewModel.deckDao.getAllDecks()
                            decks.forEach {
                                if (it.name == "T-Shirt"){
                                    viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 1)
                                }
                                else if (it.name == "Fibonacci"){
                                    viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 2)
                                }
                                else if (it.name == "Hours"){
                                    viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 3)
                                }
                                else {
                                    viewModel.setDeck(DeckEntityMapper().mapFromCached(it), 0)
                                }
                            }
                        }

                        try {
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment)
                        }catch (e:Exception){}

                    }
                    else{
                        Toast.makeText(activity as MainActivity,"Incorrect Username or Password", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Toast.makeText(activity as MainActivity,"Connection Error",Toast.LENGTH_SHORT).show()
            }
        }


        txtSignIn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment)
        }


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