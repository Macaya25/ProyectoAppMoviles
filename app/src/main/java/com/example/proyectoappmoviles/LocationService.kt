package com.example.clase12.service

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*


object LocationService {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val liveLocation = MutableLiveData<Location>()

    fun getLocation(): LiveData<Location>{
        return liveLocation
    }

    fun startLocationService(context: AppCompatActivity){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val request = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if(permission == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.requestLocationUpdates(request,object: LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if(location != null){
                        liveLocation.postValue(location)
                    }
                }
            },null)
        }
    }

    fun stopLocationService(){
        if(::fusedLocationClient.isInitialized){
            fusedLocationClient.removeLocationUpdates(locationCallback())
        }
    }
    //Distinta forma de pasar el objeto
    fun locationCallback(): LocationCallback {
        return object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                println(p0)
            }
        }
    }
}