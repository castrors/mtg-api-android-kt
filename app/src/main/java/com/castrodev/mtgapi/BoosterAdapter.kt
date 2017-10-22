package com.castrodev.mtgapi

/**
 * Created by rodrigocastro on 19/10/17.
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import org.jetbrains.anko.find


class BoosterAdapter(context: Context) :
        ArrayAdapter<MtgCard>(context, 0) {

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View? {
        var holder: ViewHolder
        var view: View? = null

        if (contentView == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.card_item_with_image, parent, false)
            holder = ViewHolder(view)

            view.setTag(holder)
        } else {
            holder = contentView.tag as ViewHolder
        }

        val card: MtgCard = getItem(position)

//        holder.name.text = card.name
//        holder.description.text = card.text
        Picasso.with(context).load(card.imageUrl).into(holder.image)

        return view
    }

    fun shiftLastToBottom(cards: List<MtgCard>){
        val switftedCards : MutableList<MtgCard> = mutableListOf()
        switftedCards.addAll(cards.subList(1,cards.size))
        switftedCards.add(cards[0])
        addAll(switftedCards)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) {
//        var name = view.find<TextView>(R.id.item_card_name)
//        var description = view.find<TextView>(R.id.item_card_description)
        var image = view.find<ImageView>(R.id.item_card_image)

    }

}

