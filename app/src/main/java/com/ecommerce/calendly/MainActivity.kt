package com.ecommerce.calendly

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ecommerce.calendly.adapter.CardViewAdapter
import com.ecommerce.calendly.adapter.Quote
import com.google.gson.JsonArray
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import okhttp3.internal.notify


class MainActivity : AppCompatActivity() {
    private lateinit var adapter :CardViewAdapter
    private var quotes = ArrayList<Quote>()
    private lateinit var progress_circular:ProgressBar
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
//        loadData(1)

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


        var requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // TODO: Inform user that that your app will not show notifications.
            }
        }

        fun askNotificationPermission() {
            // This is only necessary for API level >= 33 (TIRAMISU)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    // FCM SDK (and your app) can post notifications.
                } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    // TODO: display an educational UI explaining to the user the features that will be enabled
                    //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                    //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                    //       If the user selects "No thanks," allow the user to continue without notifications.
                } else {
                    // Directly ask for the permission
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        askNotificationPermission()
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
}


//if (objectData != null) {
//                        for (quote in objectData){
//                            quotes.add(Quote(quote.asJsonObject))
//                        }
//                    }
//                    progress_circular.visibility = View.INVISIBLE
//                    overlay.visibility = View.INVISIBLE
//                    swipeRefreshLayout.isRefreshing=false
//                    adapter.notifyDataSetChanged()