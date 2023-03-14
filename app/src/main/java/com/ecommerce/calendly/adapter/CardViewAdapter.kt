package com.ecommerce.calendly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.calendly.R

class CardViewAdapter(val quotes:List<Quote>,val context:Context) :RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var quote = quotes[position]
        holder.title.text=quote.author
        holder.description.text=quote.content
        holder.datetime.text=quote.getAddedDate()


        var adapter = IconAdapter(quote.getTags())
        var linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.recyclerView.adapter= adapter
//        holder.recyclerView.addItemDecoration(OffsetItemDecoration(20))
        if(!quote.hasBeenOffseted){
            holder.recyclerView.addItemDecoration(OffsetItemDecoration(20))
            quote.hasBeenOffseted=true
        }
        holder.recyclerView.layoutManager=linearLayoutManager
    }

    override fun getItemCount(): Int {
       return quotes.size
    }
}