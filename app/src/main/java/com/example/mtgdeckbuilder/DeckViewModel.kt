package com.example.mtgdeckbuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeckViewModel (application: Application): AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val deckRepository = DeckRepository(application.applicationContext)

    val decks = deckRepository.getAllDecks()

    var deck = MutableLiveData<Deck>()

    fun getGroupedCards(): Map<String, List<Card>> {
        return deck.value!!.cards!!.groupBy{it.types[0]}
    }

    fun getDeck(){

        ioScope.launch {
            var newDeck  = deckRepository.getDeck(deck.value?.id)
            withContext(Dispatchers.Main) {
                deck.value = newDeck
            }
        }
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