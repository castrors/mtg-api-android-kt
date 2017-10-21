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
import com.squareup.picasso.Picasso
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.card_item_with_image.view.*

class BoosterAdapter(private val sets: MutableList<MtgCard>,
                 private val context: Context,
                 private val listener: (MtgCard) -> Unit) : Adapter<BoosterAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sets[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item_with_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sets.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.card_item_name
        val description = itemView.card_item_description
        val image = itemView.card_item_image

        fun bind(card: MtgCard, listener: (MtgCard) -> Unit) = with(itemView) {
            name.text = card.name
            description.text = card.text
            Picasso.with(context).load(card.imageUrl).into(image)
            setOnClickListener{ listener(card)}
        }
    }

    fun add(newSets: List<MtgCard>) {
        sets.addAll(newSets)
        notifyDataSetChanged()
    }

}

