package com.ecommerce.calendly.adapter

import android.app.Dialog
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import com.ecommerce.calendly.R
import com.squareup.picasso.Picasso
import java.util.*

class MovieAdapter(var movies: ArrayList<Movie>, var dialog: Dialog) :
    RecyclerView.Adapter<MovieViewHolder>() {
    lateinit var title: TextView
    lateinit var year: TextView
    lateinit var type: TextView
    var imageView: ImageView? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_title, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var movie = movies[position]
        if (movie != null) {
            Picasso.get().load(movie.imageUrl).into(holder.imageView)
            holder.cardView.setOnClickListener {
                dialog.setContentView(R.layout.movie_details)
                title = dialog.findViewById(R.id.textViewTitle)
                year = dialog.findViewById(R.id.textViewYear)
                type = dialog.findViewById(R.id.textViewType)
                imageView = dialog.findViewById(R.id.imageViewPoster)
                title.text = movie.title
                year.text = movie.year
                type.text = movie.type.uppercase(Locale.getDefault())
                Picasso.get().load(movie.imageUrl).into(imageView)
                dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.setCancelable(true)
                dialog.window!!.attributes.windowAnimations = R.style.animation
                dialog.show()
            }
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}