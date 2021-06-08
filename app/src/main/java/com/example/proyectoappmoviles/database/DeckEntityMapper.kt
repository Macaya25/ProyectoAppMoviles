package com.example.proyectoappmoviles.database

import com.example.proyectoappmoviles.ObjectItems.Deck


class DeckEntityMapper: EntityMapper<DeckEntity, Deck> {
    override fun mapFromCached(type: DeckEntity): Deck {
        return Deck(
            type.name,
            type.cards.split(",").map{ it.trim()}
        )
    }

    override fun mapToCached(type: Deck): DeckEntity {
        return DeckEntity(
            type.name,
            type.cards.joinToString()
        )
    }
}