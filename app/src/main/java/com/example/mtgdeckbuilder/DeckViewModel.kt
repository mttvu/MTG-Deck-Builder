package com.example.mtgdeckbuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckViewModel (application: Application): AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val deckRepository = DeckRepository(application.applicationContext)

    val decks = deckRepository.getAllDecks()

    val deck = MutableLiveData<Deck>()

    fun getGroupedCards(): Map<String, List<Card>> {
        return deck.value!!.cards!!.groupBy{it.types[0]}
    }

    fun addCard(card: Card){
        deck.value!!.cards.add(card)
    }

    fun insertDeck(deck: Deck) {
        ioScope.launch {
            deckRepository.insertDeck(deck)
        }
    }

    fun updateDeck() {
        ioScope.launch {
            deckRepository.updateDeck(deck.value!!)
        }
    }

    fun deleteDeck(deck: Deck) {
        ioScope.launch {
            deckRepository.deleteDeck(deck)
        }
    }
}