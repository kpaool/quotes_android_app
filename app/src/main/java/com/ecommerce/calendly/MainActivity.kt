package com.ecommerce.calendly

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ecommerce.calendly.adapter.CardViewAdapter
import com.ecommerce.calendly.adapter.Quote
import com.google.gson.JsonArray
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion


class MainActivity : AppCompatActivity() {
    private lateinit var adapter :CardViewAdapter
    private var quotes = ArrayList<Quote>()
    lateinit var progress_circular:ProgressBar
    lateinit var swipeRefreshLayout :SwipeRefreshLayout
    lateinit var overlay:View
    var page:Int =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView =  findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        progress_circular  =  findViewById(R.id.progress_circular)
        overlay  =  findViewById(R.id.overlay)


        adapter=  CardViewAdapter(quotes,this@MainActivity)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=linearLayoutManager
        loadData(1)

        swipeRefreshLayout.setOnRefreshListener {
            loadData(1)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == layoutManager.itemCount - 1) {
                    loadData(page+1)
                }
            }
        })


    }

    fun loadData(pageNumber:Int){
        page=pageNumber
        progress_circular.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE
        var url = "https://quotable.io/quotes?page=$page"
        Ion.with(this)
            .load(url)
            .asJsonObject()
            .withResponse()
            .setCallback(FutureCallback { e, result ->
                run {
                    var objectData: JsonArray? = result.result.get("results").asJsonArray ?: return@FutureCallback
                    if (objectData != null) {
                        for (quote in objectData){
                            quotes.add(Quote(quote.asJsonObject))
                        }
                    }
                    progress_circular.visibility = View.INVISIBLE
                    overlay.visibility = View.INVISIBLE
                    swipeRefreshLayout.isRefreshing=false
                    adapter.notifyDataSetChanged()
                }
            })
    }
}