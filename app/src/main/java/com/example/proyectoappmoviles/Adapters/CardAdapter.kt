package com.example.proyectoappmoviles.Adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoappmoviles.ObjectItems.CardItem
import com.example.proyectoappmoviles.Fragments.InspectCardFragment
import com.example.proyectoappmoviles.Interfaces.OnClickFragmentCardInspect
import com.example.proyectoappmoviles.R

class CardAdapter(var cardsList: MutableList<CardItem>):RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    lateinit var com: OnClickFragmentCardInspect
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardsList[position]
        holder.card1.text = currentItem.cardNames[0]
        holder.card1.setOnClickListener(){
            com.onClickCardToInspect(InspectCardFragment(),currentItem.cardNames[0])
        }
        if(currentItem.cardAmount >= 2) holder.card2.text = currentItem.cardNames[1]
        if(currentItem.cardAmount == 3) holder.card3.text = currentItem.cardNames[2]




        holder.card2.setOnClickListener(){
            com.onClickCardToInspect(InspectCardFragment(),currentItem.cardNames[1])
        }
        holder.card3.setOnClickListener(){
            com.onClickCardToInspect(InspectCardFragment(),currentItem.cardNames[2])
        }


        val width = Resources.getSystem().displayMetrics.widthPixels
        if(currentItem.cardAmount == 1){
            holder.card1.x += width/3
            holder.card2.visibility = View.INVISIBLE
            holder.card3.visibility = View.INVISIBLE
        }
        else if (currentItem.cardAmount == 2){
            holder.card3.visibility = View.INVISIBLE
            holder.card1.x += width/6
            holder.card2.x += width/6
        }

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