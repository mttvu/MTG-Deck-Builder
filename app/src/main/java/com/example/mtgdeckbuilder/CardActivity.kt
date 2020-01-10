package com.example.mtgdeckbuilder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.content_card.*
import kotlinx.android.synthetic.main.item_card.ivCard


class CardActivity : AppCompatActivity() {
    private lateinit var viewModel: CardActivityViewModel
    private lateinit var deckViewModel: DeckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        setSupportActionBar(toolbar)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CardActivityViewModel::class.java)
        viewModel.card.value = intent.extras?.getParcelable(CARD_EXTRA)!!
        viewModel.card.observe(this, Observer { card ->
            if (card != null) {
//                tvTitle.text = movie.title
//                tvRelease.text = movie.getSimpleDate(movie.date)
//                tvOverview.text = movie.overview
//                tvRating.text = movie.rating.toString()
                Glide.with(this).load(card.imageUrl).into(ivCard)
            }
        })

        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        if (intent.hasExtra(DECK_EXTRA)){
        deckViewModel.deck.value = intent.extras?.getParcelable("DECK_EXTRA")!!}


    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (deckViewModel.deck.value == null) {
            btnAddToDeck.visibility = View.GONE
        } else {
            btnAddToDeck.setOnClickListener { addToDeck() }
        }

    }

    private fun addToDeck() {

            deckViewModel.addCard(viewModel.card.value as Card)
            deckViewModel.updateDeck()
            Toast.makeText(this, getString(R.string.card_added), Toast.LENGTH_SHORT).show()
            val returnIntent = Intent()
            returnIntent.putExtra(SearchActivity.EXTRA_DECK, deckViewModel.deck.value)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val CARD_EXTRA = "CARD_EXTRA"
        const val DECK_EXTRA = "DECK_EXTRA"
    }

}
