package com.example.proyectoappmoviles.ObjectItems

//Este va a ser el item para hacer create room
data class LobbyItem(val roomId:String?, val roomName:String?,val deck:Deck?, val members:List<String>?,val password:String?,val message:String?) {
}