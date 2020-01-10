package com.example.mtgdeckbuilder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*


const val REQUEST_CODE_1 = 1

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchActivityViewModel
    private val cards = arrayListOf<Card>()
    private val cardAdapter = CardAdapter(cards) { card -> onCardClick(card) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        initViewModel()
        initViews()

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        viewModel.cards.observe(this, Observer {
            cards.clear()
            for (card in it.cards) {
                if (card.imageUrl != null) {
                    cards.add(card)
                }
            }
            cardAdapter.notifyDataSetChanged()
        })

    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvCards.layoutManager = LinearLayoutManager(this)
        rvCards.adapter = cardAdapter
        btnSearch.setOnClickListener {
            viewModel.getCards(etCards.text.toString(), null, null)
        }

        fab.setOnClickListener {
            flSearch.visibility = View.GONE
            flFilter.visibility = View.VISIBLE
            fab.hide()
        }

        btnApply.setOnClickListener {
            viewModel.getCards(
                etCards.text.toString(),
                spinnerColor.selectedItem.toString(),
                spinnerType.selectedItem.toString()
            )
            flSearch.visibility = View.VISIBLE
            flFilter.visibility = View.GONE
            fab.show()
        }

    }


    private fun onCardClick(card: Card) {

        if (intent.hasExtra(EXTRA_DECK)) {
            viewModel.deck.value = intent.extras?.getParcelable(EXTRA_DECK)!!
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra(CardActivity.DECK_EXTRA, viewModel.deck.value)
            intent.putExtra(CardActivity.CARD_EXTRA, card)
            startActivityForResult(intent, REQUEST_CODE_1)
        } else {
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra(CardActivity.CARD_EXTRA, card)
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.deck.value = intent.extras?.getParcelable(EXTRA_DECK)!!
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                val returnIntent = Intent()
                returnIntent.putExtra(CardsFragment.EXTRA_DECK, viewModel.deck.value)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_DECK = "EXTRA_DECK"
    }


}
