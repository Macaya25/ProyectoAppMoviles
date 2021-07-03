package com.example.proyectoappmoviles.Adapters

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.clase12.service.LocationService
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Fragments.JoinRoomFragmentDirections
import com.example.proyectoappmoviles.Fragments.LobbyFragmentDirections
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.ObjectItems.LocationItem
import com.example.proyectoappmoviles.R

class ExampleAdapter(var exampleList: MutableList<ExampleItem>,var apiViewModel: ApiViewModel,val token:String,val activity: MainActivity,val view: View, val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {

    lateinit var ultimateNav: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.example_item,parent,false)
        ultimateNav =Navigation.findNavController(view)
        return ExampleViewHolder(itemView)
    }

    override fun getItemCount() =exampleList.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem= exampleList[position]
        ultimateNav =Navigation.findNavController(view)
        val prefs= this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        holder.textView1.text = currentItem.roomName
        holder.itemView.setOnClickListener{
            if (check_connection()){
                val temp = LobbyItem(null,null,currentItem.roomName,null,null,currentItem.roomId,null,null)
                apiViewModel.joinRoom(token,temp,activity)
                apiViewModel.joinRoomResponse.observe(activity as MainActivity, Observer { response->
                    if (response.isSuccessful){
                        apiViewModel.getRoom(token,currentItem.roomName)
                        apiViewModel.myLobby.observe(activity as MainActivity, Observer { response1->
                            if (response1.isSuccessful) {
                                //Log.d("yes",currentItem.deck.toString())
                                LocationService.getLocation().observe(lifecycleOwner, {
                                    val tempLocation = LocationItem(it.latitude.toString(), it.longitude.toString(), null, currentItem.roomName, null)
                                    //Toast.makeText(activity, it.latitude.toString()+" "+it.longitude.toString(), Toast.LENGTH_SHORT).show()
                                    apiViewModel.reportLocation(token, tempLocation)
                                    apiViewModel.reportLocationResponse.observe(activity, Observer { aux->
                                        if (aux.isSuccessful){
                                            val editor= prefs?.edit()
                                            editor?.apply {
                                                putString("CurrentJoinedRoom", currentItem.roomName as String?)
                                            }?.apply()
                                            val re = "[\\[\\]]".toRegex()
                                            val cards = response1.body()?.deck?.cards.toString()
                                            val action = LobbyFragmentDirections.actionLobbyFragmentToCardSelectorFragment(
                                                    response1.body()?.deck?.name.toString(),
                                                    re.replace(cards, "")
                                            )
                                            Navigation.findNavController(view).navigate(action)
                                        }
                                    })
                                })

                            }
                        })
                    }
                })
            }else{
                Toast.makeText(activity, "Cant Join Rooms Offline", Toast.LENGTH_SHORT).show()
            }

        }
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.findViewById(R.id.FrontendTeamText)
    }

    fun set(item:MutableList<ExampleItem>){
        exampleList=item
        this.notifyDataSetChanged()
    }

    private fun check_connection() : Boolean{
        val managment = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = managment.activeNetworkInfo
        if (networkInfo != null){
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE){
                return true
            }
        }
        return false
    }

}