package com.example.proyectoappmoviles.ViewModels

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.ObjectItems.MemberItem
import com.example.proyectoappmoviles.ObjectItems.VoteItem
import java.lang.Exception
import java.util.*

class VotesViewModel(application: Application,val context: Context) : AndroidViewModel(application) {

    var list = mutableListOf<VoteItem>()
    var live_list = MutableLiveData<MutableList<VoteItem>>()

    fun updateVotes(myVote: VoteItem){
        list.clear()
        list.add(myVote)
        live_list.postValue(list)
    }

    fun setMemberList(memberList: List<MemberItem>,voteList:List<VoteItem>){
        list.clear()
        var tempList= mutableListOf<VoteItem>()

        memberList.forEach{
            var tempMember=VoteItem(null,"",null,it.username,null,null,null,null)
            if (it.location!=null) {
                try {
                    var gc = Geocoder(context, Locale.getDefault())
                    var addresses= gc.getFromLocation(it.location.lat!!.toDouble(), it.location.long!!.toDouble(),2)
                    var address=addresses.get(0)
                    tempMember = VoteItem(null, "", null, it.username, it.location.lat, it.location.long, it.location.timestamp,address.getAddressLine(0)+" "+address.locality)
                }catch (e:Exception){
                    tempMember = VoteItem(null, "", null, it.username, it.location.lat, it.location.long, it.location.timestamp,"Unknown Location")
                }

            }
            tempList.add(tempMember)
        }

        voteList.forEach{ vote->
            tempList.forEach { member->
                if (vote.name==member.name){
                    member.vote=vote.vote
                }
            }
        }

        list=tempList
        live_list.postValue(list)
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