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
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.android.synthetic.main.card_item.view.*

class SetAdapter(private val sets: MutableList<MtgSet>,
                 private val context: Context,
                 private val listener: (MtgSet) -> Unit) : Adapter<SetAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sets[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sets.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.card_item_name
        val description = itemView.card_item_description

        fun bind(set: MtgSet, listener: (MtgSet) -> Unit) = with(itemView) {
            name.text = set.name
            description.text = set.code
            setOnClickListener{ listener(set)}
        }
    }

    fun add(newSets: List<MtgSet>) {
        sets.addAll(newSets)
        notifyDataSetChanged()
    }

}

