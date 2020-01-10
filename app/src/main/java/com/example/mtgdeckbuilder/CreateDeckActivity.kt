package com.example.mtgdeckbuilder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_create_deck.*
import kotlinx.android.synthetic.main.content_create_deck.*
import kotlinx.android.synthetic.main.item_deck.*

const val EXTRA_DECK = "EXTRA_DECK"
class CreateDeckActivity : AppCompatActivity() {
    private lateinit var viewModel: DeckViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deck)
        setSupportActionBar(toolbar)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
    }

    private fun initViews() {
        fab.setOnClickListener {
            saveDeck()
        }
    }

    private fun saveDeck() {
        if (etName.text.toString().isNotBlank()) {
            var deck = Deck(
                etName.text.toString(),
                etFormat.text.toString(),
                etDescription.text.toString(),
                arrayListOf()
            )
            viewModel.insertDeck(deck)
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_DECK, deck)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

}
