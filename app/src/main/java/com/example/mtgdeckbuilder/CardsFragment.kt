package com.example.mtgdeckbuilder


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cards.*

/**
 * A simple [Fragment] subclass.
 */
const val REQUEST_CODE_2 = 2
class CardsFragment : Fragment() {

    private lateinit var viewModel: DeckViewModel
    private val cards = mutableMapOf<String, List<Card>>()
    private val cardGroupedAdapter = CardGroupedAdapter(cards){ card -> onCardClick(card) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCards.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rvCards.adapter = cardGroupedAdapter

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity as AppCompatActivity).get(DeckViewModel::class.java)
        viewModel.deck.value = (activity as AppCompatActivity).intent.getParcelableExtra(EXTRA_DECK)!!
        viewModel.deck.observe(this, Observer {
            cards.clear()
            cards.putAll(viewModel.getGroupedCards())
            cardGroupedAdapter.notifyDataSetChanged()
        })

    }
    private fun onCardClick(card: Card){
        val intent = Intent((activity as AppCompatActivity), CardActivity::class.java)
        intent.putExtra(CardActivity.CARD_EXTRA, card)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_2) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.deck.value = (activity as AppCompatActivity).intent.getParcelableExtra(EXTRA_DECK)!!
            }
        }
    }
    companion object{
        const val EXTRA_DECK = "EXTRA_DECK"
    }

}
