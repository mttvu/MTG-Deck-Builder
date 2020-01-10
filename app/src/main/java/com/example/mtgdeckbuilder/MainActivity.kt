package com.example.mtgdeckbuilder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DeckViewModel
    private val decks = arrayListOf<Deck>()
    private val deckAdapter = DeckAdapter(decks){ deck -> onDeckClick(deck) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViewModel()
        initViews()
    }

    private fun initViews(){
        fab.setOnClickListener {
            val intent = Intent(this, CreateDeckActivity::class.java)
            startActivity(intent)
        }

        rvDecks.layoutManager = LinearLayoutManager(this)
        rvDecks.adapter = deckAdapter
        createItemTouchHelper().attachToRecyclerView(rvDecks)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        viewModel.decks.observe(this, Observer {
            decks.clear()
            decks.addAll(it)
            deckAdapter.notifyDataSetChanged()
        })
    }

    private fun onDeckClick(deck: Deck){
        val intent = Intent(this, DeckActivity::class.java)
        intent.putExtra(DeckActivity.EXTRA_DECK, deck)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deckToDelete = decks[position]

                viewModel.deleteDeck(deckToDelete)

            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}
