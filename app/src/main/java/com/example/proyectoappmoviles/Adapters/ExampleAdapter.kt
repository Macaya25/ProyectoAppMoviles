package com.example.proyectoappmoviles.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.Activities.MainActivity
import com.example.proyectoappmoviles.Api.ApiViewModel
import com.example.proyectoappmoviles.Fragments.JoinRoomFragmentDirections
import com.example.proyectoappmoviles.Fragments.LobbyFragmentDirections
import com.example.proyectoappmoviles.ObjectItems.ExampleItem
import com.example.proyectoappmoviles.ObjectItems.LobbyItem
import com.example.proyectoappmoviles.R

class ExampleAdapter(var exampleList: MutableList<ExampleItem>,var apiViewModel: ApiViewModel,val token:String,val activity: MainActivity,val view: View) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.example_item,parent,false)
        return ExampleViewHolder(itemView)
    }

    override fun getItemCount() =exampleList.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {

        val currentItem= exampleList[position]
        holder.textView1.text = currentItem.roomName
        holder.itemView.setOnClickListener{
            val temp = LobbyItem(null,null,currentItem.roomName,null,null,currentItem.roomId,null,null)
            apiViewModel.joinRoom(token,temp,activity)
            apiViewModel.joinRoomResponse.observe(activity as MainActivity, Observer { response->
                if (response.isSuccessful){
                    //TODO agregar get room
                }
            })
        }
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.findViewById(R.id.FrontendTeamText)
    }

    fun set(item:MutableList<ExampleItem>){
        exampleList=item
        this.notifyDataSetChanged()
    }

}