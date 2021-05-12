package com.example.proyectoappmoviles.database

import com.example.proyectoappmoviles.ObjectItems.ExampleItem

class RoomEntityMapper: EntityMapper<RoomEntity, ExampleItem> {
    override fun mapFromCached(type: RoomEntity): ExampleItem {
        return ExampleItem(
            type.frontendTeam
        )
    }

    override fun mapToCached(type: ExampleItem): RoomEntity {
        return RoomEntity(
            0,
            type.FrontendTeam
        )
    }
}