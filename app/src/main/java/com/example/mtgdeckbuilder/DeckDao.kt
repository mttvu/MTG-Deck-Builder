package com.example.mtgdeckbuilder

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeckDao {

    @Query("SELECT * FROM deckTable")
    fun getAllDecks(): LiveData<List<Deck>>

    @Query("SELECT * FROM deckTable WHERE id=:id ")
    fun getDeck(id: Long?): Deck

    @Insert
    fun insertDeck(deck: Deck)

    @Update
    fun updateDeck(deck: Deck)

    @Delete
    suspend fun deleteDeck(deck: Deck)
}