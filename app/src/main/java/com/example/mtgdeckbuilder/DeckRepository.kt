package com.example.mtgdeckbuilder

import android.content.Context
import androidx.lifecycle.LiveData

class DeckRepository(context: Context) {

    private var deckDao:DeckDao

    init {
        val deckRoomDatabase = DeckRoomDatabase.getDatabase(context)
        deckDao = deckRoomDatabase!!.deckDao()
    }

    fun getAllDecks(): LiveData<List<Deck>> {
        return deckDao.getAllDecks()
    }

    fun getDeck(id: Long?): LiveData<Deck>{
        return deckDao.getDeck(id)
    }

    fun updateDeck(deck: Deck) {
        deckDao.updateDeck(deck)
    }

    fun insertDeck(deck: Deck) {
        deckDao.insertDeck(deck)
    }

    suspend fun deleteDeck(deck: Deck) {
        deckDao.deleteDeck(deck)
    }

}