package com.example.proyectoappmoviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(var cardsList: MutableList<CardItem>):RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val current_item = cardsList[position]
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card1: Button = itemView.findViewById(R.id.card1)
        val card2: Button = itemView.findViewById(R.id.card2)
        val card3: Button = itemView.findViewById(R.id.card3)
    }

    fun set(item: MutableList<CardItem>){
        cardsList = item
        this.notifyDataSetChanged()
    }
}