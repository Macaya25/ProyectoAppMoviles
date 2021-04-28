package com.example.proyectoappmoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() , OnFragmentActionsListener,OnClickFragmentCardInspect{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onClickFragmentButton(LoginFragment())
    }

    override fun onClickFragmentButton(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        //fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickCardToInspect(fragment: Fragment, string: String) {
        val bundle = Bundle()
        bundle.putString("Name",string)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment.arguments= bundle
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        //fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}