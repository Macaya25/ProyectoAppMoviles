package com.example.proyectoappmoviles.Adapters

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.proyectoappmoviles.Fragments.CardSelectorFragmentDirections
import com.example.proyectoappmoviles.Fragments.DeckFragmentDirections
import com.example.proyectoappmoviles.Interfaces.OnClickFragmentCardInspect
import com.example.proyectoappmoviles.ObjectItems.CardItem
import com.example.proyectoappmoviles.ObjectItems.LocationItem
import com.example.proyectoappmoviles.ObjectItems.VoteItem
import com.example.proyectoappmoviles.R

class VoteAdapter(var cardsList: MutableList<CardItem>, var apiViewModel: ApiViewModel, val token:String, val activity: MainActivity, val view: View, val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<VoteAdapter.CardViewHolder>() {

    lateinit var com: OnClickFragmentCardInspect
    lateinit var ultimateNav:NavController


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card1: Button = itemView.findViewById(R.id.card1)
        val card2: Button = itemView.findViewById(R.id.card2)
        val card3: Button = itemView.findViewById(R.id.card3)
        var moved = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false)
        ultimateNav =Navigation.findNavController(view)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardsList[position]
        ultimateNav =Navigation.findNavController(view)

        holder.card1.text = currentItem.cardNames[0]
        if (currentItem.cardAmount >= 2) holder.card2.text = currentItem.cardNames[1]
        if (currentItem.cardAmount == 3) holder.card3.text = currentItem.cardNames[2]

        holder.card1.setOnClickListener() {
            if (check_connection()) {
                val prefs = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val roomName = prefs?.getString("CurrentRoomName", "")
                val tempVote = VoteItem(roomName, holder.card1.text.toString(), null, null,null,null,null,null)
                apiViewModel.vote(token, tempVote)
                apiViewModel.voteResponse.observe(activity, Observer { response ->
                    if (response.isSuccessful) {
                        val action = CardSelectorFragmentDirections.actionCardSelectorFragmentToVoteFragment(currentItem.cardNames[0])
                        LocationService.getLocation().observe(lifecycleOwner, {
                            val tempLocation =
                                    LocationItem(it.latitude.toString(), it.longitude.toString(), null, roomName, null)
                            //Toast.makeText(activity, it.latitude.toString()+" "+it.longitude.toString(), Toast.LENGTH_SHORT).show()
                            apiViewModel.reportLocation(token, tempLocation)
                            apiViewModel.reportLocationResponse.observe(activity, Observer { aux ->
                                if (aux.isSuccessful) {
                                    //Navigation.findNavController(view).navigate(action)
                                    ultimateNav.navigate(action)
                                }
                            })
                            //Navigation.findNavController(view).navigate(action)
                        })
                    }
                    //Navigation.findNavController(view).navigate(action)
                })
            }else{
                Toast.makeText(activity, "ERROR: Cant join without Internet", Toast.LENGTH_SHORT).show()
            }

        }
        holder.card2.setOnClickListener() {
            if (check_connection()) {
                val prefs = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val roomName = prefs?.getString("CurrentRoomName", "")
                val tempVote = VoteItem(roomName, holder.card2.text.toString(), null, null,null,null,null,null)
                apiViewModel.vote(token, tempVote)
                apiViewModel.voteResponse.observe(activity, Observer { response ->
                    if (response.isSuccessful) {
                        val action = CardSelectorFragmentDirections.actionCardSelectorFragmentToVoteFragment(currentItem.cardNames[1])
                        LocationService.getLocation().observe(lifecycleOwner, {
                            val tempLocation =
                                    LocationItem(it.latitude.toString(), it.longitude.toString(), null, roomName, null)
                            //Toast.makeText(activity, it.latitude.toString()+" "+it.longitude.toString(), Toast.LENGTH_SHORT).show()
                            apiViewModel.reportLocation(token, tempLocation)
                            apiViewModel.reportLocationResponse.observe(activity, Observer { aux ->
                                if (aux.isSuccessful) {
                                    //Navigation.findNavController(view).navigate(action)
                                    ultimateNav.navigate(action)
                                }
                            })
                            //Navigation.findNavController(view).navigate(action)
                        })
                    }

                })
            }else{
                Toast.makeText(activity, "ERROR: Cant join without Internet", Toast.LENGTH_SHORT).show()
            }
        }
        holder.card3.setOnClickListener() {
            if (check_connection()) {
                val prefs = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val roomName = prefs?.getString("CurrentRoomName", "").toString()
                val tempVote = VoteItem(roomName, holder.card3.text.toString(), null, null,null,null,null,null)
                apiViewModel.vote(token, tempVote)
                apiViewModel.voteResponse.observe(activity, Observer { response ->
                    if (response.isSuccessful) {
                        val action = CardSelectorFragmentDirections.actionCardSelectorFragmentToVoteFragment(currentItem.cardNames[2])
                        LocationService.getLocation().observe(lifecycleOwner, {
                            val tempLocation =
                                    LocationItem(it.latitude.toString(), it.longitude.toString(), null, roomName, null)
                            //Toast.makeText(activity, it.latitude.toString()+" "+it.longitude.toString(), Toast.LENGTH_SHORT).show()
                            apiViewModel.reportLocation(token, tempLocation)
                            apiViewModel.reportLocationResponse.observe(activity, Observer { aux ->
                                if (aux.isSuccessful) {
                                    //Navigation.findNavController(view).navigate(action)
                                    ultimateNav.navigate(action)
                                }
                            })
                            //Navigation.findNavController(view).navigate(action)
                        })
                    }
                    //Navigation.findNavController(view).navigate(action)
                })
            }else{
                Toast.makeText(activity, "ERROR: Cant join without Internet", Toast.LENGTH_SHORT).show()
            }
        }

        val width = Resources.getSystem().displayMetrics.widthPixels
        if (!holder.moved) {
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
            holder.moved = true
        }


    }
    override fun getItemCount(): Int {
        return cardsList.size
    }

    fun set(item: MutableList<CardItem>){
        cardsList = item
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

    fun getLocationNNavigate(action:NavDirections,roomName:String, auxView: View) {

    }

}