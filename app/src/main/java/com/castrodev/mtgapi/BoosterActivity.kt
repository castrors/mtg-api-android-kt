package com.castrodev.mtgapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.SwipeDirection
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
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

    var progressBar: ProgressBar? = null
    var cardStackView: CardStackView? = null
    var adapter: BoosterAdapter? = null

    var items: MutableList<MtgCard> = mutableListOf()
    var lastItem: MtgCard? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booster)

        val setCode = intent.getStringExtra(SET_CODE)
                ?: throw IllegalStateException("field $SET_CODE missing in Intent")
        setup()
        fetchCards(setCode)
    }

    private var count: Int = 0

    private fun setup() {
        progressBar = find(R.id.activity_booster_progress_bar)
        cardStackView = find(R.id.activity_booster_card_stack_view)
        cardStackView?.reverse()

        cardStackView?.setCardEventListener(object : CardStackView.CardEventListener {
            override fun onCardDragging(percentX: Float, percentY: Float) {
                Log.d("CardStackView", "onCardDragging")
            }

            override fun onCardSwiped(direction: SwipeDirection) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString())
                Log.d("CardStackView", "topIndex: " + cardStackView?.topIndex)
                count = count + 1
                if(count == 15){
                    adapter?.addAll(items)
                    adapter?.notifyDataSetChanged()
                    count = 0
                }
            }

            override fun onCardReversed() {
                Log.d("CardStackView", "onCardReversed")
            }

            override fun onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin")
            }

            override fun onCardClicked(index: Int) {
                Log.d("CardStackView", "onCardClicked: " + index)
            }
        })
    }

    private fun fetchCards(setCode: String) {
        cardStackView?.visibility = View.GONE
        progressBar?.visibility = View.VISIBLE
        adapter = BoosterAdapter(this)
        doAsync {
            val cardsResponse: Response<List<MtgCard>> = MtgSetApiClient.generateBoosterPackBySetCode(setCode)
            val cards = cardsResponse.body() ?: mutableListOf()
            items = cards as MutableList<MtgCard>
            uiThread {
                adapter?.addAll(cards)
                cardStackView?.setAdapter(adapter)
                cardStackView?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
            }
        }
    }
}
