package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.VoteItem

class VotesViewModel(application: Application) : AndroidViewModel(application) {

    var list = mutableListOf<VoteItem>()
    var live_list = MutableLiveData<MutableList<VoteItem>>()

    fun updateVotes(myVote: VoteItem){
        list.clear()
        list.add(myVote)
        live_list.postValue(list)
    }

    fun setMemberList(memberList: List<String>,voteList:List<VoteItem>){
        list.clear()

        var tempList= mutableListOf<String>()
        voteList.forEach{
            tempList.add(it.name.toString())
            val tempMember=VoteItem(null,it.vote,null,it.name)
            list.add(tempMember)
            live_list.postValue(list)
        }

        memberList.forEach{
            if (it !in tempList){
                val tempMember=VoteItem(null,"",null,it)
                list.add(tempMember)
                live_list.postValue(list)
            }
        }

        /*voteList.forEach{
            if (it.name in memberList){
                val tempMember=VoteItem(null,it.vote,null,it.name)
                list.add(tempMember)
                live_list.postValue(list)
            }else{
                val tempMember=VoteItem(null,"",null,it.name)
                list.add(tempMember)
                live_list.postValue(list)
            }
        }*/
    }

}