package com.ecommerce.calendly.adapter

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.ecommerce.calendly.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView =  itemView.findViewById(R.id.title)
    var description: TextView =  itemView.findViewById(R.id.description)
    var datetime: TextView  = itemView.findViewById(R.id.textViewDateTime)
    var recyclerView : RecyclerView = itemView.findViewById(R.id.recyclerViewIcons)

}

class OffsetItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.left = 0
            outRect.left = -offset // apply negative offset to the left margin
        }
    }
}