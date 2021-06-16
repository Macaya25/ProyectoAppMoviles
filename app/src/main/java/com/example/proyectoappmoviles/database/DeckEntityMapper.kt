package com.example.proyectoappmoviles.database

import com.example.proyectoappmoviles.ObjectItems.Deck


class DeckEntityMapper {
    fun mapFromCached(type: DeckEntity?): Deck {
        return if (type == null){
            Deck("", listOf(""))
        }
        else {
            Deck(
                    type.name,
                    type.cards.split(",").map{ it.trim()}
            )
        }
    }

    fun mapToCached(type: Deck?): DeckEntity {
        return if (type == null){
            DeckEntity("", "")
        }
        else{
            DeckEntity(
                    type.name,
                    type.cards.joinToString()
            )
        }

    }
}