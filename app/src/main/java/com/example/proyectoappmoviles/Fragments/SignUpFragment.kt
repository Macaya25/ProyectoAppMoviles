package com.example.proyectoappmoviles.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
//import com.example.proyectoappmoviles.Api.ApiViewModelFactory
import com.example.proyectoappmoviles.Api.Repository
import com.example.proyectoappmoviles.Api.UserObject
import com.example.proyectoappmoviles.R
import org.koin.android.ext.android.inject


class SignUpFragment : Fragment() {

    private val apiViewModel: ApiViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_sign_up, container, false)

        //val repository= Repository()
        //val viewModelFactory= ApiViewModelFactory(requireActivity().application, repository)
        //apiViewModel= ViewModelProvider(this,viewModelFactory).get(ApiViewModel::class.java)

        val bttn = view.findViewById<Button>(R.id.RegisterButton)

        bttn.setOnClickListener{
            val username= view.findViewById<EditText>(R.id.UsernameRegisterPlainText).text.toString()
            val name= view.findViewById<EditText>(R.id.NameRegisterPlainText).text.toString()
            val password= view.findViewById<EditText>(R.id.PasswordRegisterPlainText).text.toString()
            val myUser=UserObject(null,username,name,password,null)
            apiViewModel.getSignUp(myUser)
            apiViewModel.myResponse.observe(activity as MainActivity, Observer { response->
                if(response.isSuccessful){
                    Toast.makeText(activity as MainActivity,"Successful User Creation", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment)
                }
                else{
                    Toast.makeText(activity as MainActivity,"Credential Already Taken", Toast.LENGTH_SHORT).show()
                }
            })
        }


        return view
    }


}