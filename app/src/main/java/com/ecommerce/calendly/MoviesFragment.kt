package com.ecommerce.calendly

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.calendly.adapter.Movie
import com.ecommerce.calendly.adapter.MovieAdapter
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import java.util.ArrayList

class MoviesFragment : Fragment() {

    private val movies= ArrayList<Movie>()
    private lateinit var dialog :Dialog
    lateinit var imageViewBanner : ImageView
    lateinit var movieAdapter: MovieAdapter
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view:View = inflater.inflate(R.layout.fragment_movies, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        imageViewBanner = view.findViewById(R.id.imageViewBanner)
        dialog= context?.let { Dialog(it) }!!



        movieAdapter = MovieAdapter(movies,dialog)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = movieAdapter


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
        val baseUrl = "http://www.omdbapi.com/?"
        val apikey = "650fc0f9"
        val search = "batman"
        val getMovieEndpoint = baseUrl + "apikey=" + apikey + "&s=" + search
        //        String getMovieEndpoint ="http://www.omdbapi.com/?apikey=650fc0f9&s=batman";
        Ion.with(this)
            .load(getMovieEndpoint)
            .asJsonObject()
            .setCallback(FutureCallback { e, result ->
                if (e != null) {
                    return@FutureCallback
                }
                val returnedMovies = result["Search"].asJsonArray
                for (i in 0 until returnedMovies.size()) {
                    movies.add(
                        Movie(returnedMovies[i].asJsonObject)
                    )
                }
                movies.shuffle()
                movieAdapter.notifyDataSetChanged()
                val min = 0
                val max = movies.size
                val a = Math.random() * (max - min + 1) + min
                Picasso.get().load(movies[a.toInt()]!!.imageUrl).into(imageViewBanner)
            })
    }
}