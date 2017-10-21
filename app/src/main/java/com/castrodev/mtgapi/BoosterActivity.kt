package com.castrodev.mtgapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Response

class BoosterActivity : AppCompatActivity() {

    companion object {

        private val SET_CODE = "set_code"

        fun newIntent(context: Context, setCode: String): Intent {
            val intent = Intent(context, BoosterActivity::class.java)
            intent.putExtra(SET_CODE, setCode)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        val setCode = intent.getStringExtra(SET_CODE)
                ?: throw IllegalStateException("field $SET_CODE missing in Intent")

        val recyclerView = recyclerView
        val cardsAdapter = BoosterAdapter(mutableListOf<MtgCard>(), this) {
            toast("${it.name} Clicked")
        }
        recyclerView.adapter = cardsAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager


        fetchCards(setCode, cardsAdapter)
    }


    private fun fetchCards(setCode: String, cardsAdapter: BoosterAdapter) {
        doAsync {
            val cardsResponse: Response<List<MtgCard>> = MtgSetApiClient.generateBoosterPackBySetCode(setCode)
            val cards = cardsResponse.body() ?: mutableListOf()
            uiThread {
                cardsAdapter.add(cards)
            }
        }
    }
}
