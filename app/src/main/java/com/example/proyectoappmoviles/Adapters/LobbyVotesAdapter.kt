package com.example.proyectoappmoviles.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.ObjectItems.VoteItem
import com.example.proyectoappmoviles.R

class LobbyVotesAdapter(var cardsList: MutableList<VoteItem>): RecyclerView.Adapter<LobbyVotesAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userNameView: TextView = itemView.findViewById(R.id.votingUserName)
        val votingScore: TextView = itemView.findViewById(R.id.votingScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vote_item,parent,false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardsList[position]
        holder.userNameView.text = currentItem.name
        holder.votingScore.text = currentItem.vote
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    fun set(item: MutableList<VoteItem>){
        cardsList = item
        this.notifyDataSetChanged()
    }
}