package com.example.proyectoappmoviles.database

import com.example.proyectoappmoviles.ObjectItems.ExampleItem

class RoomEntityMapper: EntityMapper<RoomEntity, ExampleItem> {
    override fun mapFromCached(type: RoomEntity): ExampleItem {
        return ExampleItem(
            type.room_id,
            type.name!!,
            type.waitingDelete,
            type.madeOffline,
            DeckEntityMapper().mapFromCached(type.deck)
        )
    }

    override fun mapToCached(type: ExampleItem): RoomEntity {
        return RoomEntity(
            type.roomId,
            type.roomName,
            type.waitingDelete,
            type.madeOffline,
            DeckEntityMapper().mapToCached(type.deck)
        )
    }
}