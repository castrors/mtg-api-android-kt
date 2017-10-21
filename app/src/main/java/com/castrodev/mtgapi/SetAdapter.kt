package com.castrodev.mtgapi

/**
 * Created by rodrigocastro on 19/10/17.
 */
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.card_item.view.*

class BoosterAdapter(private val cards: MutableList<MtgCard>,
                     private val context: Context,
                     private val listener: (MtgCard) -> Unit) : Adapter<BoosterAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.card_item_name
        val description = itemView.card_item_description

        fun bind(card: MtgCard, listener: (MtgCard) -> Unit) = with(itemView) {
            name.text = card.name
            description.text = card.text
            setOnClickListener{ listener(card)}
        }
    }

    fun add(newCards: List<MtgCard>) {
        cards.addAll(newCards)
        notifyDataSetChanged()
    }

}

