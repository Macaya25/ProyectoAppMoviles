package com.example.proyectoappmoviles.Activities

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.clase12.service.LocationService
import com.example.proyectoappmoviles.Fragments.LoginFragment
import com.example.proyectoappmoviles.Interfaces.OnClickFragmentCardInspect
import com.example.proyectoappmoviles.Interfaces.OnFragmentActionsListener
import com.example.proyectoappmoviles.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askPermissions()
        Thread.sleep(3000);
        LocationService.startLocationService(this)
    }

    private fun askPermissions(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),200)
        }
    }

}