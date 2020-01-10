package com.example.mtgdeckbuilder


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_card.view.tvName
import kotlinx.android.synthetic.main.item_deck.view.*

class DeckAdapter(private val decks: List<Deck>, private val onClick: (Deck) -> Unit):
    RecyclerView.Adapter<DeckAdapter.ViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_deck, parent, false)
        )
    }

    override fun getItemCount(): Int = decks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(decks[position])
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(decks[adapterPosition]) }
        }
        fun bind(deck: Deck) {
            itemView.tvName.text = deck.name
            itemView.tvFormat.text = deck.format
            //Glide.with(context).load(card.imageUrl).into(itemView.ivCard)
        }

    }
}