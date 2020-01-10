package com.example.mtgdeckbuilder

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_deck.*
import kotlinx.android.synthetic.main.content_deck.*


class DeckActivity : AppCompatActivity() {
    private lateinit var viewModel: DeckViewModel

    private val tabAdapter = TabAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)
        setSupportActionBar(toolbar)

        initViewModel()
        initViews()
        initTabs()

        val groupedByType = viewModel.deck.value?.cards?.groupBy{it.types[0]}
        println(groupedByType?.keys)

    }
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        viewModel.deck.value = intent.extras?.getParcelable(com.example.mtgdeckbuilder.EXTRA_DECK)!!

    }
    private fun initViews(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(SearchActivity.EXTRA_DECK, viewModel.deck.value)
            startActivityForResult(intent, REQUEST_CODE_2)
        }
    }

    private fun initTabs(){
        tabAdapter.addFragment(CardsFragment(), "Cards")
        tabAdapter.addFragment(InfoFragment(), "Info")

        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_2){
        val fragment: Fragment? =
            supportFragmentManager.fragments[0]
        fragment?.onActivityResult(requestCode, resultCode, intent)}
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

    companion object{
        const val EXTRA_DECK = "EXTRA_DECK"
    }

}
