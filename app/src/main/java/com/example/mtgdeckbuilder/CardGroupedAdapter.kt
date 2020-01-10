package com.example.mtgdeckbuilder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_card.view.*

class CardGroupedAdapter(private val cards: Map<String, List<Card>>, private val onClick: (Card) -> Unit):
    RecyclerView.Adapter<CardGroupedAdapter.ViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_section, parent, false)
        )
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val cardList = cards.values.toMutableList()
        holder.type?.text = cardList[position][0].types[0]
            holder.rv?.layoutManager = LinearLayoutManager(holder.rv.context)
        var adapter = CardAdapter(cardList[position], onClick)
        holder.rv?.adapter = adapter
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type = itemView.findViewById<TextView>(R.id.tvType)
        val rv = itemView.findViewById<RecyclerView>(R.id.rvCards)

    }
}