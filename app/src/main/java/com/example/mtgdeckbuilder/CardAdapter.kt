package com.example.mtgdeckbuilder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(private val cards: List<Card>, private val onClick: (Card) -> Unit):
    RecyclerView.Adapter<CardAdapter.ViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        )
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(cards[position])
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(cards[adapterPosition]) }
        }
        fun bind(card: Card) {
            itemView.tvName.text = card.name
            itemView.tvType.text = card.type
            itemView.tvMana.text = card.manaCost
            Glide.with(context).load(card.imageUrl).into(itemView.ivCard)
        }

    }
}