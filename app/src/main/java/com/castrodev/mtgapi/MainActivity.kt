package com.castrodev.mtgapi

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Button
import android.widget.TextView
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = recyclerViewCards
        val cardsAdapter = MyAdapter(mutableListOf<MtgCard>(), this)
        recyclerView.adapter = cardsAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        val textViewSet = findViewById<TextView>(R.id.textViewSet)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)

        buttonSearch.setOnClickListener {
            fetchCards(textViewSet.text.toString(), cardsAdapter, this)
        }

    }

    private fun fetchCards(setCode: String, cardsAdapter: MyAdapter, context: Context) {
        doAsync {
            val cardsResponse: Response<List<MtgCard>> = MtgSetApiClient.generateBoosterPackBySetCode(setCode)
            val cards = cardsResponse.body()
            uiThread {
                toast("success")
                cards?.let {
                    cards.forEach {
                        toast(it.name)
                    }
                    cardsAdapter.add(cards)

                }
            }
        }
    }
}


