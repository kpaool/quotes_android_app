package com.ecommerce.calendly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.calendly.adapter.Coin
import com.ecommerce.calendly.adapter.CoinAdapter
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlin.random.Random

class CoinsFragment : Fragment() {

    private var coins = ArrayList<Coin>()
    private lateinit var adapter : CoinAdapter
    lateinit var progressIndicator : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_coins, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView)
        progressIndicator= view.findViewById(R.id.progress_circular)
        val linearLayoutManager: LinearLayoutManager =  LinearLayoutManager(context)
        adapter= CoinAdapter(coins)
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter=adapter

        loadData()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadData(){
        progressIndicator.visibility = ContentLoadingProgressBar.VISIBLE
        val url = "https://api.coinpaprika.com/v1/coins"
        Ion.with(this)
            .load(url)
            .asJsonArray()
            .withResponse()
            .setCallback(FutureCallback { e, result ->
                run {
                    val array = result.result
                    var i = 0
                    for (item in array) {
                        if (i == 10) break
                        var coin: Coin? = context?.let { Coin(item.asJsonObject, it, adapter) }
                        if (coin != null) {
                            coin.lookForUrl()
                            coins.add(
                                coin
                            )
                        }

                        i += 1
                    }
                    coins.shuffle(Random)
                    adapter.notifyDataSetChanged()
                    progressIndicator.visibility = ContentLoadingProgressBar.INVISIBLE
                }
            })
    }


}