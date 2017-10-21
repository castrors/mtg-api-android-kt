package com.castrodev.mtgapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Response

class SetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        val recyclerView = recyclerView
        val setAdapter = SetAdapter(mutableListOf<MtgSet>(), this) {
            toast("${it.name} Clicked")
            val intent = BoosterActivity.newIntent(this, it.code)
            startActivity(intent)
        }
        recyclerView.adapter = setAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        fetchSets(setAdapter)
    }

    private fun fetchSets(setAdapter: SetAdapter) {
        doAsync {
            val setsResponse: Response<List<MtgSet>> = MtgSetApiClient.getAllSets()
            val sets = setsResponse.body()
            uiThread {
                toast("success")
                sets?.let { setAdapter.add(sets) }
            }
        }
    }
}


