package com.example.proyectoappmoviles.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= [RoomEntity::class, DeckEntity::class], version = 1, exportSchema = false)
abstract class RoomsDatabase: RoomDatabase() {
    abstract fun roomDao(): RoomDao
    abstract fun deckDao(): DeckDao

    /*
    companion object{
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): RoomDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "room_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
        }

         */

}