package com.example.mtgdeckbuilder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Deck::class], version = 1, exportSchema = false)
@TypeConverters(CardsConverter::class)
abstract class DeckRoomDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao

    companion object {
        private const val DATABASE_NAME = "DECK_DATABASE"

        @Volatile
        private var deckRoomDatabaseInstance: DeckRoomDatabase? = null

        fun getDatabase(context: Context): DeckRoomDatabase? {
            if (deckRoomDatabaseInstance == null) {
                synchronized(DeckRoomDatabase::class.java) {
                    if (deckRoomDatabaseInstance == null) {
                        deckRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            DeckRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return deckRoomDatabaseInstance
        }
    }

}