package com.example.proyectoappmoviles.database

import com.example.proyectoappmoviles.ObjectItems.ExampleItem

class RoomEntityMapper: EntityMapper<RoomEntity, ExampleItem> {
    override fun mapFromCached(type: RoomEntity): ExampleItem {
        return ExampleItem(
            type.room_id,
            type.name,
            null,
        null
        )
    }

    override fun mapToCached(type: ExampleItem): RoomEntity {
        return RoomEntity(
            0,
            type.room_id,
            type.name
        )
    }
}