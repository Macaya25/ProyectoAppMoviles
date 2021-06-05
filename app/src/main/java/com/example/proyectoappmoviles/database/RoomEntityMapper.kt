package com.example.proyectoappmoviles.database

import com.example.proyectoappmoviles.ObjectItems.ExampleItem

class RoomEntityMapper: EntityMapper<RoomEntity, ExampleItem> {
    override fun mapFromCached(type: RoomEntity): ExampleItem {
        return ExampleItem(
            type.name,
            type.password,
            type.deck
        )
    }

    override fun mapToCached(type: ExampleItem): RoomEntity {
        return RoomEntity(
            0,
            type.name,
            type.password,
            type.deck
        )
    }
}