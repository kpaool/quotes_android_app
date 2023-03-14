package com.ecommerce.calendly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ecommerce.calendly.adapter.CardViewAdapter
import com.ecommerce.calendly.adapter.Quote
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class HomeFragment : Fragment() {

    private lateinit var adapter : CardViewAdapter
    private var quotes = ArrayList<Quote>()
    lateinit var progress_circular: ProgressBar
    lateinit var swipeRefreshLayout : SwipeRefreshLayout
    lateinit var overlay:View
    var page:Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view :View = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)!!
        progress_circular  = view.findViewById(R.id.progress_circular)!!
        overlay  = view.findViewById(R.id.overlay)!!

        adapter= context?.let { CardViewAdapter(quotes, it) }!!
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
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

        // Inflate the layout for this fragment
        return view


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
    }

    fun loadData(pageNumber:Int){
        page=pageNumber
        progress_circular.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE

        var url = "https://quotable.io/quotes?page=$page"
        Ion.with(this)
            .load(url)
            .asJsonObject()
            .setCallback(FutureCallback { e, result ->
                run {
                    var objectData =result.get("results").asJsonArray
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

//    companion object {
//
//        @JvmStatic fun newInstance(param1: String, param2: String) =
//                HomeFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}