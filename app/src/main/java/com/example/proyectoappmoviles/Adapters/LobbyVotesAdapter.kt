package com.example.proyectoappmoviles.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.ObjectItems.VoteItem
import com.example.proyectoappmoviles.R
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class LobbyVotesAdapter(var cardsList: MutableList<VoteItem>): RecyclerView.Adapter<LobbyVotesAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userNameView: TextView = itemView.findViewById(R.id.votingUserName)
        val votingScore: TextView = itemView.findViewById(R.id.votingScore)
        val locationText: TextView = itemView.findViewById(R.id.VoteLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vote_item,parent,false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardsList[position]
        holder.userNameView.text = currentItem.name
        holder.votingScore.text = currentItem.vote
        if (currentItem.direction==null){
            holder.locationText.text="OFFLINE"
        }else{
            if (timeDiff(currentItem.time!!)<60){
                holder.locationText.text=currentItem.direction
            }else{
                holder.locationText.text="OFFLINE"
            }
        }

    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    fun set(item: MutableList<VoteItem>){
        cardsList = item
        this.notifyDataSetChanged()
    }

    
    fun timeDiff(apiDate:String): Long {
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("gmt")
        val d1 = Date()

        var converted = ""
        val dt = apiDate.split(" ").toList()
        val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Sep", "Oct", "Nov", "Dec")
        var month_id = months.indexOf(dt[1]) + 1
        if (month_id < 10){
            converted += "0" + month_id.toString()
        }
        else{
            converted += month_id.toString()
        }
        converted+="/" + dt[2] + "/" + dt[3] + " " + dt[4]
        val d2 = sdf.parse(converted)


        val diffInMs: Long = abs(d2.getTime() - d1.getTime())
        return TimeUnit.MILLISECONDS.toSeconds(diffInMs)
    }










}