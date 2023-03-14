package com.ecommerce.calendly.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ecommerce.calendly.R

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView
    var cardView: MaterialCardView

    init {
        imageView = itemView.findViewById(R.id.imageView)
        cardView = itemView.findViewById(R.id.cardViewMovieItem)
    }
}