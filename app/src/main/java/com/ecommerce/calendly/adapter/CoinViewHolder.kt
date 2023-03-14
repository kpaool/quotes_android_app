package com.ecommerce.calendly.adapter

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.calendly.R

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var icon:ImageView = itemView.findViewById(R.id.imageViewIcon)
    var title:TextView = itemView.findViewById(R.id.textViewTitle)
    var description:TextView = itemView.findViewById(R.id.textViewDescription)
    var indicator:CardView = itemView.findViewById(R.id.indicator)



}